package com.isoftstone.agiledev.osgi.db.dao;
/**
 * 基于commons-dbcp.BasicDataSource创建mybatis-SqlSession.
 * 使用无配置文件形式。mapper和domain来自其他bundle动态注册
 * @author david
 */
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



public class SessionFactory implements SessionCreater {


	private Properties config = null;
	public SessionFactory() {
		super();
		config = this.loadProperties();
	}

	@Override
	public SqlSession openSession() {
		try {

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
		return AppUtils.getRuntime();
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
