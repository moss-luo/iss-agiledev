package com.isoftstone.agiledev.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.isoftstone.agiledev.config.manager.ConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManagerFactory;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigReaderFactory {
	
	public static List<String> readCallbacks()
	{
		List<String> callbacks = new ArrayList<String>();
		ConfigManager config = ConfigManagerFactory.getCallbacksConfigManager();
		Set<String> keys = config.keys();
		for(String s : keys)
			callbacks.add(s);
		return callbacks;
	}
	
}
