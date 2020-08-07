package com.smartonet.project.core.ioc;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Creat by hanzhao
 * on 2019/8/1
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerSetter = "setOnClickListener",//点击事件
        listenerType = View.OnClickListener.class,//接口
        callBackMethod = "onClick")//接口回调
public @interface OnClick {
    /**
     * 需要点击事件的View
     * @return
     */
    int[] value();
}
