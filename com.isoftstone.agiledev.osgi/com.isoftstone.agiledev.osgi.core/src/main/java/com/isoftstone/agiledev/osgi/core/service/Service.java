package com.isoftstone.agiledev.osgi.core.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来标示bundle中的service
 * @author david
 *
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Service {

	/**
	 * 接口类型,如果只有一个接口，可以不写。如果有多个接口，必须指定一个接口类型
	 * @return
	 */
	Class<?> type() default Object.class;
	/**
	 * 注册service时的serviceName
	 * @return
	 */
	String name() default "";
}
