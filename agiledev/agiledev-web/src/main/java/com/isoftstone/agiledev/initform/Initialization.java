package com.isoftstone.agiledev.initform;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Initialization {
	Initializa[] value() default {};
}
