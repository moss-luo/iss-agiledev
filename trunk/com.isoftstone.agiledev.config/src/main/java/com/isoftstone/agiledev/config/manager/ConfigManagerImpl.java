package com.isoftstone.agiledev.config.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.isoftstone.agiledev.config.exception.ConfigException;


/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class ConfigManagerImpl implements ConfigManager {

	protected Properties properties = new Properties();
	
	public ConfigManagerImpl(String propertiesName)
	{
		this(null, propertiesName);
	}
	
	public ConfigManagerImpl(String keyOfPropertiesPath, String propertiesName)
	{
		Object obj = null;
		if(keyOfPropertiesPath != null && keyOfPropertiesPath.trim().length() != 0)
			obj = ConfigContextManagerImpl.getInstance().get(keyOfPropertiesPath);
        if (obj == null)
        {
        	//search from default directory
        	loadDefaultProps(propertiesName);
        } else {
        	String callbacksConfigPath = (String) obj;
        	File file = new File(callbacksConfigPath);
        	if (file.isDirectory())
        	{
        		file = new File(callbacksConfigPath + "/" + propertiesName);
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
    				loadDefaultProps(propertiesName);
    		} else {
    			loadDefaultProps(propertiesName);
    		}
        }
	}
	
	private void loadDefaultProps(String propertiesName)
	{
		String virgohome = System.getProperty("catalina.base");
		File f = new File(virgohome + "/configuration/agiledev/" + propertiesName);
		if(f.exists())
		{
			try {
				properties.load(new FileInputStream(f));
			} catch (IOException e) {
				throw new ConfigException(e);
			}
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
