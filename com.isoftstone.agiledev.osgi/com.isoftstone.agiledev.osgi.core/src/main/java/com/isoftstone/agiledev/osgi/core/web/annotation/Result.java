package com.isoftstone.agiledev.osgi.core.web.annotation;
/**
 * Action返回的Result注解，name表示return的字符串;
 * type标示返回的类型，@see Result;
 * params标示返回的参数，要求数组的第一个是参数名称，第二个是参数值,目前要求参数名称为root，参数值为要输出的对象
 * location标示要输出的页面路径，只有在type是redirect或者forward时有用
 *  
 *  @author david
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Result {

	String name() default "success";
	String type() default "json";
	String[] params() default {};
	String location() default "";
}
