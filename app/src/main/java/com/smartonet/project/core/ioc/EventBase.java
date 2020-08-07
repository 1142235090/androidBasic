package com.smartonet.project.core.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creat by hanzhao
 * on 2019/8/1
 **/
@Retention(RetentionPolicy.RUNTIME)//运行时加载
@Target(ElementType.ANNOTATION_TYPE)//定义在注解上使用
public @interface EventBase {
    /**
     * 事件监听的方法
     * @return
     */
    String listenerSetter();
    /**
     * 事件类型
     * @return
     */
    Class<?> listenerType();
    /**
     * 事件回调方法
     * 事件触发后 执行回调方法
     * @return
     */
    String callBackMethod();
}
