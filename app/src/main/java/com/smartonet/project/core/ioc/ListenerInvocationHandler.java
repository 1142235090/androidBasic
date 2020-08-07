package com.smartonet.project.core.ioc;

import android.content.Context;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Creat by hanzhao
 * on 2019/8/1
 **/
public class ListenerInvocationHandler implements InvocationHandler {
    private Context context;
    private Map<String,Method> methodMap;
    public ListenerInvocationHandler(Context context, Map<String, Method> methodMap) {
        this.context = context;
        this.methodMap = methodMap;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName=method.getName();//获取绑架事件监听的函数
        Method med= methodMap.get(methodName);//根据函数名获取事件监听方法
        if(null!=med){
            return med.invoke(context,args);
        }else {
            return method.invoke(proxy,args);
        }
    }
}
