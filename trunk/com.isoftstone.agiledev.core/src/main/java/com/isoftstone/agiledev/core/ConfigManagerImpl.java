package com.isoftstone.agiledev.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.core.io.Resource;

public class ConfigManagerImpl implements ConfigManager {
	private Map<String, Object> configs;
	
	public ConfigManagerImpl(Resource[] resources) {
		configs = new HashMap<String, Object>();
		readConfigs(resources);
	}

	private void readConfigs(Resource[] resources) {
		// TODO Auto-generated method stub
		for (Resource resource : resources) {
			InputStream is = null;
			try {
				is = resource.getInputStream();
				Properties properties = new Properties();
				properties.load(is);
				
				for (Object key : properties.keySet()) {
					configs.put((String)key, properties.get(key));
				}
			} catch (Exception e) {
				continue;
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						// do nothing
					}
				}
			}
		}
	}

	@Override
	public Object get(String name) {
		return configs.get(name);
	}

	@Override
	public Set<String> keys() {
		return configs.keySet();
	}

	@Override
	public Collection<Object> values() {
		return configs.values();
	}

}
