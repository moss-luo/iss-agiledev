package com.isoftstone.agiledev.config.manager.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.isoftstone.agiledev.config.exception.ConfigException;
import com.isoftstone.agiledev.config.manager.AbstractConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManagerFactory;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class CallbacksConfigManagerImpl extends AbstractConfigManager implements ConfigManager {
	
	public CallbacksConfigManagerImpl()
	{
		Object obj = ConfigManagerFactory.getConfigContextManager().get("callbacks.path");
        if (obj == null)
        {
        	//search from default directory
        	loadDefaultProps();
        } else {
        	String callbacksConfigPath = (String) obj;
        	File file = new File(callbacksConfigPath);
        	if (file.isDirectory())
        	{
        		file = new File(callbacksConfigPath + "/callbacks.properties");
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
		File f = new File(virgohome + "/configuration/agiledev/callbacks.properties");
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
