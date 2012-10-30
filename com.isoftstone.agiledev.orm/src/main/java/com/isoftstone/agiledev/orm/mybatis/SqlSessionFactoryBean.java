package com.isoftstone.agiledev.orm.mybatis;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.session.TransactionIsolationLevel;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

public class SqlSessionFactoryBean implements FactoryBean<SqlSessionFactory>,
		InitializingBean, ApplicationListener<ApplicationEvent>, BundleContextAware,
			BundleListener {
	private SqlSessionFactoryProxy sqlSessionFactoryProxy;
	private boolean failFast;
	private DataSource dataSource;
	private TransactionFactory transactionFactory;
	private String environment = SqlSessionFactoryBean.class.getSimpleName();
	private Interceptor[] plugins;
	private TypeHandler<?>[] typeHandlers;
	private String typeHandlersPackage;
	private Class<?>[] typeAliases;
	private String typeAliasesPackage;
	private Properties configurationProperties;
	private SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
	private Resource configLocation;
	private Map<Long, Resource[]> mapperResources;
	private BundleContext bundleContext;

	private static final String KEY_MAPPER_LOCATIONS = "MyBatis-Mapper-Locations";

	private static final Logger logger = LoggerFactory
			.getLogger(SqlSessionFactoryBean.class);

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setTransactionFactory(TransactionFactory transactionFactory) {
		this.transactionFactory = transactionFactory;
	}

	public void setPlugins(Interceptor[] plugins) {
		this.plugins = plugins;
	}

	public void setTypeHandlers(TypeHandler<?>[] typeHandlers) {
		this.typeHandlers = typeHandlers;
	}

	public void setTypeHandlersPackage(String typeHandlersPackage) {
		this.typeHandlersPackage = typeHandlersPackage;
	}

	public void setTypeAliases(Class<?>[] typeAliases) {
		this.typeAliases = typeAliases;
	}

	public void setTypeAliasesPackage(String typeAliasesPackage) {
		this.typeAliasesPackage = typeAliasesPackage;
	}

	public void setConfigLocation(Resource configLocation) {
		this.configLocation = configLocation;
	}

	public void setConfigurationProperties(Properties configurationProperties) {
		this.configurationProperties = configurationProperties;
	}

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		if (failFast && event instanceof ContextRefreshedEvent) {
			// fail-fast -> check all statements are completed
			this.sqlSessionFactoryProxy.getConfiguration()
					.getMappedStatementNames();
		}
	}

	public void setFailFast(boolean failFast) {
		this.failFast = failFast;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(dataSource, "Property 'dataSource' is required");
		Assert.notNull(sqlSessionFactoryBuilder,
				"Property 'sqlSessionFactoryBuilder' is required");

		if (bundleContext == null)
			throw new IllegalStateException("Can't get bundle context");
		
		mapperResources = new HashMap<Long, Resource[]>();
		this.sqlSessionFactoryProxy = new SqlSessionFactoryProxy();
		bundleContext.addBundleListener(this);
		synchronized (this) {
			scanExistingMappers();
			buildSqlSessionFactory();
		}
	}
	
	private void scanExistingMappers() {
		for (Bundle bundle : bundleContext.getBundles()) {
			if (bundle.getState() == Bundle.ACTIVE) {
				addMappersIfNecessary(bundle);
			}
		}
	}

	private void addMappersIfNecessary(Bundle bundle) {
		Resource[] resources = scanMappers(bundle);
		if (resources != null && resources.length != 0) {
			mapperResources.put(bundle.getBundleId(), resources);
		}
	}

	private Resource[] scanMappers(Bundle bundle) {
		Dictionary<String, String> header = bundle.getHeaders();
		if (header == null)
			return new Resource[0];
		
		String mapperLocations = header.get(KEY_MAPPER_LOCATIONS);
		if (mapperLocations == null) {
			return new Resource[0];
		}
			
		List<Resource> resources = new ArrayList<Resource>();
		StringTokenizer tokenizer = new StringTokenizer(mapperLocations, ",");
		while (tokenizer.hasMoreElements()) {
			String sLocationAndPattern = tokenizer.nextToken();
			String location = sLocationAndPattern;
			String pattern = "**/*.xml"; // default pattern

			int index = sLocationAndPattern.indexOf(';');
			if (index != -1) {
				location = sLocationAndPattern.substring(0, index);
				if (index != sLocationAndPattern.length() - 1) {
					pattern = sLocationAndPattern.substring(index + 1);
				}
				
				if (pattern.startsWith("pattern=")) {
					pattern = pattern.substring(8, pattern.length());
				}
			}
			
			boolean recurse = false;
			String entryPattern = pattern;
			if (pattern.startsWith("**/*")) {
				recurse = true;
				entryPattern = pattern.substring(3, pattern.length());
			}

			if (!entryPattern.startsWith("*")) {
				throw new IllegalArgumentException(String.format(
					"Illegal mapper locations definition. Bad match pattern: %s",
								pattern));
			}
			
			scanMappers(bundle, location, entryPattern, recurse, resources);
		}
		
		Resource[] resourcesArray = new Resource[resources.size()];
		return resources.toArray(resourcesArray);
	}

	private void scanMappers(Bundle bundle, String location, String entryPattern,
			boolean recurse, List<Resource> resources) {
		Enumeration<URL> urls = bundle.findEntries(location, entryPattern, recurse);
		if (urls == null)
			return;
		
		while (urls.hasMoreElements()) {
			URL url = urls.nextElement();
			if (!resources.contains(url)) {
				resources.add(new UrlResource(url));
			}
		}
	}

	private void buildSqlSessionFactory() throws IOException {
		Configuration configuration;

		XMLConfigBuilder xmlConfigBuilder = null;
		if (this.configLocation != null) {
			xmlConfigBuilder = new XMLConfigBuilder(
					this.configLocation.getInputStream(), null,
					this.configurationProperties);
			configuration = xmlConfigBuilder.getConfiguration();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("Property 'configLocation' not specified, using default MyBatis Configuration");
			}
			configuration = new Configuration();
			configuration.setVariables(this.configurationProperties);
		}

		if (StringUtils.hasLength(this.typeAliasesPackage)) {
			String[] typeAliasPackageArray = StringUtils.tokenizeToStringArray(
					this.typeAliasesPackage,
					ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeAliasPackageArray) {
				configuration.getTypeAliasRegistry().registerAliases(
						packageToScan);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan
							+ "' for aliases");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.typeAliases)) {
			for (Class<?> typeAlias : this.typeAliases) {
				configuration.getTypeAliasRegistry().registerAlias(typeAlias);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type alias: '" + typeAlias + "'");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.plugins)) {
			for (Interceptor plugin : this.plugins) {
				configuration.addInterceptor(plugin);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered plugin: '" + plugin + "'");
				}
			}
		}

		if (StringUtils.hasLength(this.typeHandlersPackage)) {
			String[] typeHandlersPackageArray = StringUtils
					.tokenizeToStringArray(
							this.typeHandlersPackage,
							ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
			for (String packageToScan : typeHandlersPackageArray) {
				configuration.getTypeHandlerRegistry().register(packageToScan);
				if (logger.isDebugEnabled()) {
					logger.debug("Scanned package: '" + packageToScan
							+ "' for type handlers");
				}
			}
		}

		if (!ObjectUtils.isEmpty(this.typeHandlers)) {
			for (TypeHandler<?> typeHandler : this.typeHandlers) {
				configuration.getTypeHandlerRegistry().register(typeHandler);
				if (logger.isDebugEnabled()) {
					logger.debug("Registered type handler: '" + typeHandler
							+ "'");
				}
			}
		}

		if (xmlConfigBuilder != null) {
			try {
				xmlConfigBuilder.parse();

				if (logger.isDebugEnabled()) {
					logger.debug("Parsed configuration file: '"
							+ this.configLocation + "'");
				}
			} catch (Exception ex) {
				throw new NestedIOException("Failed to parse config resource: "
						+ this.configLocation, ex);
			} finally {
				ErrorContext.instance().reset();
			}
		}

		if (this.transactionFactory == null) {
			this.transactionFactory = new SpringManagedTransactionFactory(
					this.dataSource);
		}

		Environment environment = new Environment(this.environment,
				this.transactionFactory, this.dataSource);
		configuration.setEnvironment(environment);

		for (Resource mapperLocation : getMapperLocations()) {
			if (mapperLocation == null) {
				continue;
			}

			// this block is a workaround for issue
			// http://code.google.com/p/mybatis/issues/detail?id=235
			// when running MyBatis 3.0.4. But not always works.
			// Not needed in 3.0.5 and above.
			String path;
			if (mapperLocation instanceof ClassPathResource) {
				path = ((ClassPathResource) mapperLocation).getPath();
			} else {
				path = mapperLocation.toString();
			}

			try {
				XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(
						mapperLocation.getInputStream(), configuration, path,
						configuration.getSqlFragments());
				xmlMapperBuilder.parse();
			} catch (Exception e) {
				throw new NestedIOException(
						"Failed to parse mapping resource: '" + mapperLocation
								+ "'", e);
			} finally {
				ErrorContext.instance().reset();
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Parsed mapper file: '" + mapperLocation + "'");
			}
		}

		sqlSessionFactoryProxy.setTarget(this.sqlSessionFactoryBuilder
				.build(configuration));
	}
	
	private Resource[] getMapperLocations() {
		List<Resource> all = new ArrayList<Resource>();
		for (Resource[] resources : mapperResources.values()) {
			for (Resource resource : resources) {
				all.add(resource);
			}
		}
		
		Resource[] resources = new Resource[all.size()];
		return all.toArray(resources);
	}

	@Override
	public SqlSessionFactory getObject() throws Exception {
		if (this.sqlSessionFactoryProxy == null) {
			afterPropertiesSet();
		}

		return this.sqlSessionFactoryProxy;
	}

	@Override
	public Class<?> getObjectType() {
		return this.sqlSessionFactoryProxy == null ? SqlSessionFactory.class
				: this.sqlSessionFactoryProxy.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	private class SqlSessionFactoryProxy implements SqlSessionFactory {
		private SqlSessionFactory target;

		public void setTarget(SqlSessionFactory target) {
			this.target = target;
		}

		@Override
		public SqlSession openSession() {
			return target.openSession();
		}

		@Override
		public SqlSession openSession(boolean autoCommit) {
			return target.openSession(autoCommit);
		}

		@Override
		public SqlSession openSession(Connection connection) {
			return target.openSession(connection);
		}

		@Override
		public SqlSession openSession(TransactionIsolationLevel level) {
			return target.openSession(level);
		}

		@Override
		public SqlSession openSession(ExecutorType execType) {
			return target.openSession(execType);
		}

		@Override
		public SqlSession openSession(ExecutorType execType, boolean autoCommit) {
			return target.openSession(execType, autoCommit);
		}

		@Override
		public SqlSession openSession(ExecutorType execType,
				TransactionIsolationLevel level) {
			return target.openSession(execType, level);
		}

		@Override
		public SqlSession openSession(ExecutorType execType,
				Connection connection) {
			return target.openSession(execType, connection);
		}

		@Override
		public Configuration getConfiguration() {
			return target.getConfiguration();
		}

	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getType() == BundleEvent.STARTED) {
			synchronized (this) {
				addMappersIfNecessary(event.getBundle());
				try {
					buildSqlSessionFactory();
				} catch (IOException e) {
					throw new RuntimeException("Can't build SqlSessionFactory.", e);
				}
			}
		} else if (event.getType() == BundleEvent.STOPPED) {
			synchronized (this) {
				mapperResources.remove(event.getBundle().getBundleId());
				try {
					buildSqlSessionFactory();
				} catch (IOException e) {
					throw new RuntimeException("Can't build SqlSessionFactory.", e);
				}
			}
		} else {
			// do nothing
		}
	}
}
