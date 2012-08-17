package com.isoftstone.agiledev.core.init;

import java.lang.annotation.*;
/**
 * 标示某字段可被初始化
 * 如果数组长度为0，则认为是标示注解，具体的初始值要在被标示的对象中去找。
 * 如果数组长度不为0，则将Initializa[]中的值直接做为初始值输出。
 * @author sinner
 *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Initialization {
	Init[] value() default {};
}
