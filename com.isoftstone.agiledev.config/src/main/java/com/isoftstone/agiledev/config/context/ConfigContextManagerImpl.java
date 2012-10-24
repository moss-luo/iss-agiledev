package com.isoftstone.agiledev.config.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.isoftstone.agiledev.config.AbstractConfigManager;
import com.isoftstone.agiledev.config.ConfigManager;
import com.isoftstone.agiledev.config.SysUtils;
import com.isoftstone.agiledev.config.exception.ConfigException;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigContextManagerImpl extends AbstractConfigManager implements ConfigManager
{
	
	public ConfigContextManagerImpl()
	{
		File contextFile = SysUtils.getConfigContext();
		try {
			properties.load(new FileInputStream(contextFile));
		} catch (IOException e) {
			throw new ConfigException(e);
		}
	}

}
