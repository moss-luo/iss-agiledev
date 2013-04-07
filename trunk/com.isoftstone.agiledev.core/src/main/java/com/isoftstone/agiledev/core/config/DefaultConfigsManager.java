package com.isoftstone.agiledev.core.config;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultConfigsManager implements ConfigsManager {
	private String configDir;
	private Map<String, ConfigManager> configManagers;
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultConfigsManager.class);
	
	@Override
	public void setParameters(String[] parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException("null config dir");
		}
		
		if (parameters.length != 1) {
			throw new IllegalArgumentException("can't determine config dir");
		}
		
		configDir = replaceSystemProperties(parameters[0]);
	}
	
	private String replaceSystemProperties(String configDir) {
		int placeholderStart = configDir.indexOf("$[");
		if (placeholderStart == -1) {
			return configDir;
		}
		
		int placeholderEnd = configDir.indexOf(']');
		if (placeholderEnd == -1)
			return configDir;
		
		if (placeholderEnd < placeholderStart)
			return configDir;
		
		StringBuilder sb = new StringBuilder();
		sb.append(configDir.substring(0, placeholderStart));
		
		String sp = System.getProperty(configDir.substring(placeholderStart + 2, placeholderEnd));
		if (sp == null) {
			throw new RuntimeException(String.format("can't read system property[%s]",
				configDir.substring(placeholderStart + 2, placeholderEnd)));
		}
		sb.append(sp);
		sb.append(configDir.substring(placeholderEnd + 1));
		
		return replaceSystemProperties(sb.toString());
	}

	protected void readConfigs() {
		File fConfigDir = new File(configDir);
		if (fConfigDir.exists()) {
			if (!fConfigDir.isDirectory()) {
				throw new RuntimeException(String.format("%s isn't a directory", configDir));
			}
		} else {
			throw new RuntimeException(String.format("%s doesn't exist", configDir));
		}
		
		File[] files = fConfigDir.listFiles();
		Map<String, URL> configURLs = new HashMap<String, URL>();
		for (File file : files) {
			if (file.isFile()) {
				String fileName = file.getName();
				if (!fileName.endsWith(".properties"))
					continue;
				
				String domain = fileName.substring(0, fileName.length() - 11);
				try {
					configURLs.put(domain, file.toURI().toURL());
				} catch (MalformedURLException e) {
					if (logger.isWarnEnabled()) {
						logger.warn("Can't convert %s to URL", file.getPath());
					}
				}
			}
		}
		
		Iterator<Entry<String, URL>> iter = configURLs.entrySet().iterator();
		while (iter.hasNext()) {
			Entry<String, URL> urlEntry = iter.next();
			configManagers.put(urlEntry.getKey(), new ConfigManagerImpl(urlEntry.getKey(), urlEntry.getValue()));
		}
	}

	public Map<String, ConfigManager> getConfigManagers() {
		if (configManagers == null) {
			configManagers = new HashMap<String, ConfigManager>();
			readConfigs();
		}
		
		return configManagers;
	}

	@Override
	public ConfigManager getConfigManager(String configDomain) {
		return getConfigManagers().get(configDomain);
	}
}
