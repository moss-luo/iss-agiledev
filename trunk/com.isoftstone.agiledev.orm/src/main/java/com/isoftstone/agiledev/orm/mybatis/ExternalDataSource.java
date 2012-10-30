package com.isoftstone.agiledev.orm.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.dbcp.BasicDataSource;

import com.isoftstone.agiledev.config.exception.ConfigException;
import com.isoftstone.agiledev.config.manager.ConfigManager;
import com.isoftstone.agiledev.config.manager.ConfigManagerFactory;

public class ExternalDataSource extends BasicDataSource{
//default-init-method
	protected String configurationLocation=null;
	public void setConfigurationLocation(String location){
		configurationLocation = location;
	}
	
	@PostConstruct
	public void init() throws Exception{
		if(configurationLocation==null || "".equals(configurationLocation)){
			ConfigManager dataSourceConfig = ConfigManagerFactory.getDataSourceconfigManager();
			if(dataSourceConfig.get("jdbc.driverClassName")!=null){
				this.setDriverClassName(dataSourceConfig.get("jdbc.driverClassName").toString());
				this.setUrl(dataSourceConfig.get("jdbc.url").toString());
				this.setUsername(dataSourceConfig.get("jdbc.username").toString());
				this.setPassword(dataSourceConfig.get("jdbc.password").toString());
			}
		}else if(configurationLocation.startsWith("${user.dir}")){
			String path = System.getProperty("user.dir")+File.separator+configurationLocation.split(":")[1];
			Properties prop = new Properties();
			File f = new File(path);
			if(f.exists()){
				prop.load(new FileInputStream(f));
				this.setDriverClassName(prop.getProperty("jdbc.driverClassName"));
				this.setUrl(prop.getProperty("jdbc.url"));
				this.setUsername(prop.getProperty("jdbc.username"));
				this.setPassword(prop.getProperty("jdbc.password"));
			}else{
				throw new ConfigException("文件不存在:"+path);
			}
		}
	}
}
