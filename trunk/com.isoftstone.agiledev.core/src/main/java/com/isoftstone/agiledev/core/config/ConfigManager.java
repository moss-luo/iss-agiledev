package com.isoftstone.agiledev.core.config;

import java.util.Set;

public interface ConfigManager {
	String getDomain();
	Object get(String key);
	Set<String> keys();
}
