package com.isoftstone.agiledev.config.manager;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;


/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public abstract class AbstractConfigManager implements ConfigManager {

	protected Properties properties = new Properties();
	
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
