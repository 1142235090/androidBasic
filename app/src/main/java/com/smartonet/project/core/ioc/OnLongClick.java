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
@EventBase(listenerSetter = "setOnLongClickListener",
        listenerType = View.OnLongClickListener.class,
        callBackMethod = "onLongClick")
public @interface OnLongClick {
    /**
     * 需要长按事件的View
     * @return
     */
    int [] value();
}
