package com.isoftstone.agiledev.validater;

import java.lang.annotation.*;
/**
 * 在Action中标示某对象的字段需要校验
 * @see com.isoftstone.agiledev.validater.Validation
 * @author sinner
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.FIELD,ElementType.METHOD})
public @interface Validations {
	Validation[] value() default {};

}
