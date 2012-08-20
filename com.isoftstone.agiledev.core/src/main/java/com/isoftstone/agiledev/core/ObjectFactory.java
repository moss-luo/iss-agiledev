package com.isoftstone.agiledev.core;

public interface ObjectFactory {
	<T> T create(Class<T> clazz);
}
