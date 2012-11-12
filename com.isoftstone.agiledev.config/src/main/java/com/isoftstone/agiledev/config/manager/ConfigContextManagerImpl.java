package com.isoftstone.agiledev.config.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.isoftstone.agiledev.config.SysUtils;
import com.isoftstone.agiledev.config.exception.ConfigException;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigContextManagerImpl implements ConfigManager
{
	
	private Properties properties = new Properties();
	
	private static final ConfigContextManagerImpl INSTANCE = new ConfigContextManagerImpl();
	
	public static final ConfigContextManagerImpl getInstance()
	{
		return INSTANCE;
	}
	
	private ConfigContextManagerImpl()
	{
		File contextFile = SysUtils.getConfigContext();
		try {
			properties.load(new FileInputStream(contextFile));
		} catch (IOException e) {
			throw new ConfigException(e);
		}
	}

	@Override
	public Object get(String name) {
		return properties.get(name);
	}

	@Override
	public Set<String> keys() {
		Set<String> keys = new HashSet<String>();
		Enumeration<Object> keyEnum = properties.keys();
		while(keyEnum.hasMoreElements())
		{
			keys.add((String)keyEnum.nextElement());
		}
		return keys;
	}

	@Override
	public Collection<Object> values() {
		return properties.values();
	}

}
