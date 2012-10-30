package com.isoftstone.agiledev.config.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.isoftstone.agiledev.config.exception.ConfigException;
import com.isoftstone.agiledev.config.manager.AbstractConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManagerFactory;

public class DataSourceConfigManagerImpl extends AbstractConfigManager
		implements ConfigManager {

	

	public DataSourceConfigManagerImpl()
	{
		Object obj = ConfigManagerFactory.getConfigContextManager().get("dataSource.path");
        if (obj == null)
        {
        	//search from default directory
        	loadDefaultProps();
        } else {
        	String dataSourceConfigPath = (String) obj;
        	File file = new File(dataSourceConfigPath);
        	if (file.isDirectory())
        	{
        		file = new File(dataSourceConfigPath + "/jdbc.properties");
        	}
        	
        	if(file.exists())
    		{
    			try {
					properties.load(new FileInputStream(file));
				} catch (FileNotFoundException e) {
					throw new ConfigException(e);
				} catch (IOException e) {
					throw new ConfigException(e);
				}
    			
    			if(properties.isEmpty())
    				loadDefaultProps();
    		} else {
    			loadDefaultProps();
    		}
        }
	}
	private void loadDefaultProps()
	{
		String virgohome = System.getProperty("catalina.base");
		File f = new File(virgohome + "/configuration/agiledev/jdbc.properties");
		if(f.exists())
		{
			try {
				properties.load(new FileInputStream(f));
			} catch (IOException e) {
				throw new ConfigException(e);
			}
		}
	}
}
