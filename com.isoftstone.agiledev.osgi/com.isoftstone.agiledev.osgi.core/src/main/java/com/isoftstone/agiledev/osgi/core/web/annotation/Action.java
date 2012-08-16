package com.isoftstone.agiledev.osgi.core.web.annotation;
/**
 * 标示当前类为Action，packageName/path标示了当前类的请求路径。
 * 如：web/list,会请求web模块下的ListAction(前提是ListAction标注了Action注解)，并执行execute方法
 *    web/op/install,会请求web模块下的OperationAction，并执行install方法
 *    
 * @author david
 */
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
public @interface Action {

	String path();
	String packageName() default "";
	Result[] results() default {};
}
