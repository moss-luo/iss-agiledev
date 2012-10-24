package com.isoftstone.agiledev.config.manager;

import com.isoftstone.agiledev.config.manager.impl.CallbacksConfigManagerImpl;
import com.isoftstone.agiledev.config.manager.impl.ConfigContextManagerImpl;


/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigManagerFactory {
	
	private static final ConfigContextManagerImpl configContextManager = new ConfigContextManagerImpl();
	private static final CallbacksConfigManagerImpl callbacksConfigManager = new CallbacksConfigManagerImpl();
	
	public static ConfigManager getConfigContextManager()
	{
		return configContextManager;
	}

	public static ConfigManager getCallbacksConfigManager()
	{
		return callbacksConfigManager;
	}
	
}
