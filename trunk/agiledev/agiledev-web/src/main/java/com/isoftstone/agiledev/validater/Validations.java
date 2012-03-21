package com.isoftstone.agiledev.validater;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.METHOD})
public @interface Validations {
	Validation[] value() default {};

}
