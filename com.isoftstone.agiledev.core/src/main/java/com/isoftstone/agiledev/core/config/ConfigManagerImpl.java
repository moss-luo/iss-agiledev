package com.isoftstone.agiledev.core.config;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class ConfigManagerImpl implements ConfigManager {
	private Map<String, Object> configs;
	private String domain;
	
	public ConfigManagerImpl(String domain, URL url) {
		this.domain = domain;
		configs = new HashMap<String, Object>();
		readConfigs(url);
	}
	
	public void readConfigs(URL url) {
		InputStream is = null;
		try {
			is = url.openStream();
			Properties properties = new Properties();
			properties.load(is);
			
			for (Object key : properties.keySet()) {
				configs.put((String)key, properties.get(key));
			}
		} catch (IOException e) {
			throw new RuntimeException("Can't load properties", e);
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

	@Override
	public Object get(String key) {
		return configs.get(key);
	}

	@Override
	public Set<String> keys() {
		return configs.keySet();
	}

	@Override
	public String getDomain() {
		return domain;
	}

}
