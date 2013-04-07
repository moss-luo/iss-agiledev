package com.isoftstone.agiledev.core;

import com.isoftstone.agiledev.core.config.ReadOnlyConfigsManager;

public interface ObjectFactory extends ReadOnlyConfigsManager {
	<T> T create(Class<T> clazz);
	Object get(String id);
}
