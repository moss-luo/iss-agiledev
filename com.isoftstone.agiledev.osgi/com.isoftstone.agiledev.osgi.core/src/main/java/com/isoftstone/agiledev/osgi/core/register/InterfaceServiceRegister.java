package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;

public interface InterfaceServiceRegister extends BeanRegister{

	Class<?> getParentType();
	
	Class<?>[] getClasses();
}
