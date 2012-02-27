package com.isoftstone.agiledev.validater;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Remotely {

	String url();
	String parameterName() default "p";
	String method();
}
