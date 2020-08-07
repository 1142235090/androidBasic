package com.smartonet.project.core.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//该注解使用在类上
@Retention(RetentionPolicy.RUNTIME)//运行时
public @interface AutowaireLayout
{
	int value();
}
