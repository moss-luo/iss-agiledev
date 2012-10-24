package com.isoftstone.agiledev.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.isoftstone.agiledev.config.exception.ConfigException;

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
        	//TODO search from default directory
        } else {
        	String callbacksConfigPath = (String) obj;
        	File file = new File(callbacksConfigPath);
        	if (file.isDirectory())
        	{
        		//TODO certain file
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
    		} else {
    			//TODO
    		}
        }
	}

}
