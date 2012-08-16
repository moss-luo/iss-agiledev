package com.isoftstone.agiledev.osgi.core.domain;
/**
 * mapper注解，标示当前接口为MyBatis的Mapper类型
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
public @interface EntityMapper {

}
