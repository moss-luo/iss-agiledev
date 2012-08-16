package com.isoftstone.agiledev.osgi.core.web.annotation;

public @interface InterceptorRef {
	String[] values() default {};
}
