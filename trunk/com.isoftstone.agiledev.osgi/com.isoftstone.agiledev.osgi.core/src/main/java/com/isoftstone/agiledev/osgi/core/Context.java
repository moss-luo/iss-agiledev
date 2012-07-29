package com.isoftstone.agiledev.osgi.core;

import java.util.Dictionary;

public interface Context {

	void setClassLoader(ClassLoader classLoader);

	@SuppressWarnings("rawtypes")
	void registerService(String name,Object o,Dictionary props);
	@SuppressWarnings("rawtypes")
	void registerService(Class<?> clazz,Object o,Dictionary props);
	
	void registerService(String name,Object o);
	void registerService(Class<?> clazz,Object o);
	
	void registerDomain(String alias,Class<?> clazz);
	void registerMapper(Class<?> clazz);

	void unregisterService(String name);
	void unregisterService(String name,String filter);
	void unregisterService(Class<?> clazz);
	void unregisterService(Class<?> clazz,String filter);
	
	Class<?>[] getContextClasses();
}
