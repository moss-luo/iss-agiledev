package com.isoftstone.agiledev.osgi.core.domain;
/**
 * Domain注解，标示当前类的alias
 * @author david
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntityAlias {
	String value();
}
