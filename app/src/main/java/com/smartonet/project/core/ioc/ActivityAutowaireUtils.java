package com.smartonet.project.core.ioc;

import android.content.Context;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;


public class ActivityAutowaireUtils {

    private static final String TAG = "ActivityAutowaireUtils";

    public static void inject(Context context) {
        //注入主布局文件
        injectContentView(context);
        //注入控件
        injectViews(context);
        //注入事件
        injectEvents(context);
    }


    /**
     * 注入界面上的控件
     * @param activity
     */
    private static void injectViews(Context activity) {
        Class<? extends Context> clazz = activity.getClass();
        Field[] fields = clazz.getDeclaredFields();
        // 遍历成员变量判断是否启用了AutowaireLayout注解
        for (Field field : fields) {
            AutowaireView autowaireViewAnnotation = field.getAnnotation(AutowaireView.class);
            if (autowaireViewAnnotation != null) {
                int viewId = autowaireViewAnnotation.value();
                if (viewId != -1) {
                    try {
                        Method method = clazz.getMethod("findViewById", int.class);
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, resView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取布局文件并添加进入界面中
     * @param activity
     */
    private static void injectContentView(Context activity) {
        Class<? extends Context> clazz = activity.getClass();
        // 查询类上是否存在ContentView注解
        AutowaireLayout autowaireLayout = clazz.getAnnotation(AutowaireLayout.class);
        if (autowaireLayout != null){
            int contentViewLayoutId = autowaireLayout.value();
            try {
                Method method = clazz.getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(activity, contentViewLayoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 首先根据函数上的注解判断是什么类型的事件
     * 然后通过动态代理去获取控件以及打上注解的方法
     * 根据注解的callBackMethod去获取控件的事件，使只对应到注解方法中
     * @param context
     */
    private static void injectEvents(Context context) {
        Class<?> clazz=context.getClass();
        //保存函数对应的事件回调方法
        Map<String,Method> methodMap=new HashMap<>();
        //获取Activtiy里面所有的方法
        Method[] methods=clazz.getDeclaredMethods();
        //遍历
        for(Method method:methods){
            //获取该函数上的所有注解
            Annotation[] annotations=method.getAnnotations();
            //遍历该函数注解
            for (Annotation annotation:annotations){
                //获取该注释的注释类型
                Class<?> anntionType=annotation.annotationType();
                //获取注解上面的EventBase的注解
                EventBase eventBase=anntionType.getAnnotation(EventBase.class);
                //判空
                if(null==eventBase) {continue;}
                //获取EventBase注解3个函数的返回值
                // 也就是事件三要素(监听的方法,事件类型，回调函数)
                String listenerSetter = eventBase.listenerSetter();
                //事件类型 长按 还是点击
                Class<?> listenerType = eventBase.listenerType();
                //事件回调--onClick()
                String backMethod = eventBase.callBackMethod();

                //将该函数与对应的事件回调方法保存到map中
                methodMap.put(backMethod,method);
                try {
                    //(getDeclaredMethod)获取的是类自身声明的所有方法，包含public、protected和private方法
                    Method valueMethod = anntionType.getDeclaredMethod("value");
                    //获取函数注解的返回值(view id)
                    int[] viewIds = (int[]) valueMethod.invoke(annotation);
                    for(int viewId:viewIds){
                        //(getMethod)类的所有共有方法 获取findViewById函数
                        Method findViewById = clazz.getMethod("findViewById", int.class);
                        //执行findViewById得到View
                        View view = (View) findViewById.invoke(context, viewId);
                        if (null==view){continue;}
                        //反射获取view的事件监听方法（事件函数，事件类型）
                        Method setListener = view.getClass().getMethod(listenerSetter, listenerType);
                        //创建代理类对象
                        ListenerInvocationHandler handler=new ListenerInvocationHandler(context,methodMap);
                        //proxyInstance实现listenerType(事件类型)接口
                        Object proxyInstance = Proxy.newProxyInstance(anntionType.getClassLoader(),
                                new Class[]{listenerType}, handler);

                        //执行事件回调方法
                        setListener.invoke(view,proxyInstance);
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
