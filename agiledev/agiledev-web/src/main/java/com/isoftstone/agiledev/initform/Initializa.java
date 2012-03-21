package com.isoftstone.agiledev.initform;

import java.lang.annotation.*;
/**
 * 标示某字段的初始化值
 * @author sinner
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Initializa {
	String fieldName() default "";
	String value() default "";
}
