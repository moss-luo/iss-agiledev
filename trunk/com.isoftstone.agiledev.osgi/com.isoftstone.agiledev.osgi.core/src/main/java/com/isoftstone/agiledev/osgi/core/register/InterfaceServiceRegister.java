package com.isoftstone.agiledev.osgi.core.register;


public interface InterfaceServiceRegister extends BeanRegister{

	Class<?> getParentType();
	
	Class<?>[] getClasses();
}
