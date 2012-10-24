package com.isoftstone.agiledev.config;

import java.util.Collection;
import java.util.Set;

public interface ConfigManager {
	
	Object get(String name);

	Set<String> keys();

	Collection<Object> values();
	
}
