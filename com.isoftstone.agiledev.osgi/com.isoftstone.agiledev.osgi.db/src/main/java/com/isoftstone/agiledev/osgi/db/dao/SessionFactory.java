package com.isoftstone.agiledev.osgi.db.dao;
/**
 * 基于commons-dbcp.BasicDataSource创建mybatis-SqlSession.
 * 使用无配置文件形式。mapper和domain来自其他bundle动态注册
 * @author david
 */
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.isoftstone.agiledev.osgi.commons.util.AppUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;



public class SessionFactory implements SessionCreater {

//	private BundleContext context = null;

//	private Logger logger = LoggerFactory.getLogger(SessionFactory.class);
	private Properties config = null;
	public SessionFactory() {
		super();
		config = this.loadProperties();
	}

//	public SessionFactory(BundleContext context) {
//		super();
//		this.context = context;
//	}
	@Override
	public SqlSession openSession() {
		try {

//			ServiceReference[] sf = context.getServiceReferences(
//					DataSource.class.getName(), "(component.name=dataSource)");
//			DataSource ds = null;
//			if (sf != null && sf.length > 0) {
//				ds = (DataSource) context.getService(sf[0]);
//			}
			BasicDataSource ds = new BasicDataSource();
			ds.setUrl(getProperty("jdbc.url"));
			ds.setUsername(getProperty("jdbc.username"));
			ds.setPassword(getProperty("jdbc.password"));
			ds.setDriverClassName(getProperty("jdbc.driverClassName"));
			ds.setMaxIdle(3600);
			
			TransactionFactory transactionFactory = new JdbcTransactionFactory();
			Environment environment = new Environment("development",
					transactionFactory, ds);
			Configuration configuration = new Configuration(environment);
			configuration.setCacheEnabled(true);
			configuration.setDefaultStatementTimeout(25000);
//			for (Class<?> c: this.domains) {
//				if(c.isAnnotationPresent(EntityAlias.class)){
//					EntityAlias entity = (EntityAlias) c.getAnnotation(EntityAlias.class);
//					String alias = entity.value(); 
//					configuration.getTypeAliasRegistry().registerAlias(alias,c);
//				}else{
//					configuration.getTypeAliasRegistry().registerAlias(c.getName(),c);
//				}
//			}
			for (String s : this.domains.keySet()) {
				configuration.getTypeAliasRegistry().registerAlias(s,this.domains.get(s));
			}
			for (Class<?> c : this.mappers) {
				if(!configuration.hasMapper(c))
					configuration.addMapper(c);
			}
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			SqlSessionFactory factory = builder.build(configuration);
			return factory.openSession();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Map<String,Class<?>> domains = new HashMap<String,Class<?>>();
	private List<Class<?>> mappers = new ArrayList<Class<?>>();
	private String getProperty(String key){
		if(config == null)
			config = this.loadProperties();
		return config.getProperty(key);
	}
	private Properties loadProperties(){
		 String appHome = AppUtils.getAppHome();
        String dbfile = appHome+"/config/agile-db.properties";
//        logger.info("Dbconfig file path:"+dbfile);
        Properties prop = new Properties();
        try {
			prop.load(new FileInputStream(new File(dbfile)));
		} catch (Exception e) {
			e.printStackTrace();
		}
        return prop;
	}
	
	@Override
	public void registerDomain(String alias,Class<?> c) {
		domains.put(alias,c);
	}

	@Override
	public void registerMapper(Class<?> c) {
		mappers.add(c);
		
	}
}
