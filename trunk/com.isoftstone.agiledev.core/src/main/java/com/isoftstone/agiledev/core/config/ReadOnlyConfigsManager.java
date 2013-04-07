package com.isoftstone.agiledev.core.config;

import java.util.Map;

public interface ReadOnlyConfigsManager {
	Map<String, ConfigManager> getConfigManagers();
	ConfigManager getConfigManager(String configDomain);
}
