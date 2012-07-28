package com.isoftstone.agiledev.osgi.core.web;

import java.lang.reflect.Method;



public interface ActionInvocation {

	Action getAction();
	
	ValueStack getStack();
	
	Method getMethod();
	
	void setMethodParameter(Object[] values);
	
	String invock();
}
