package com.isoftstone.agiledev.config.manager;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public abstract class ConfigManagerFactory {
	
	private static final ConfigManager callbacksConfigManager = new ConfigManagerImpl("callbacks.path", "callbacks.properties");
	private static final ConfigManager dataSourceConfigManager = new ConfigManagerImpl("dataSource.path", "jdbc.properties");
	private static final ConfigManager commonConfigManager = new ConfigManagerImpl("common.path", "common.properties");
	
	public static ConfigManager getCallbacksConfigManager()
	{
		return callbacksConfigManager;
	}
	
	public static ConfigManager getDataSourceconfigManager(){
		return dataSourceConfigManager;
	}

	public static ConfigManager getCommonconfigmanager() {
		return commonConfigManager;
	}
	
}
