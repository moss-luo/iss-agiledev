package com.isoftstone.agiledev.validater;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 标示某字段的校验信息
 * 如果作为Validations的数组元素出现，下列三个属性都需要根据情况配置
 * 如果作为字段标示出现，则无须配置
 * @author sinner
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Validation {

	String fieldName() default "";
	Class<? extends Annotation> validType() default Override.class;
	String[] params() default {};
}
