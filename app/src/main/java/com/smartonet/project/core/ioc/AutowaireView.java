package com.smartonet.project.core.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)//应用在成员变量上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface AutowaireView
{
	int value();
}
