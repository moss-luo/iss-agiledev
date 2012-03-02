package com.isoftstone.agiledev.query;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.isoftstone.agiledev.query.QueryParameters;
import com.opensymphony.xwork2.ActionInvocation;

public class QueryParametersMapInjector implements QueryParametersInjector {
	private Log log = LogFactory.getLog(QueryParametersMapInjector.class);
	
	private String defaultDb;
	
	private Map<String, QueryParametersDbAdaptor> adaptors;
	
	@SuppressWarnings("unchecked")
	@Override
	public void inject(ActionInvocation invocation, QueryParameters parameters) {
		Object action = invocation.getAction();
		Class<?> clazz = action.getClass();

		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Map.class.isAssignableFrom(field.getType())) {
					QueryParametersMap queryParameters = field
							.getAnnotation(QueryParametersMap.class);
					if (queryParameters == null)
						continue;

					String dbType = queryParameters.db();

					if (dbType == null || "".equals(dbType)) {
						dbType = defaultDb;
					}

					boolean oldAccessible = field.isAccessible();
					Map<String, Object> parametersMap = null;
					try {
						field.setAccessible(true);
						parametersMap = (Map<String, Object>) field.get(action);
					} catch (ClassCastException e) {
						throw new IllegalArgumentException("Field annotated with @QueryParametersMap must be type of java.utils.Map", e);
					} catch (Exception e) {
						if (log.isDebugEnabled()) {
							log.debug("Can't get query parameters map.", e);
						}
						continue;
					} finally {
						field.setAccessible(oldAccessible);
					}
					
					if (parametersMap != null) {
						QueryParametersDbAdaptor adaptor = adaptors.get(dbType);
						
						if (adaptor != null) {
							adaptor.adapt(parameters, parametersMap);
						}
					}
				}
			}

			clazz = clazz.getSuperclass();
		}
	}

	@Override
	public void init(Properties properties) {
		if (properties != null) {
			defaultDb = (String)properties.get("default-db");
		}
		
		if (defaultDb == null) {
			defaultDb = "mysql";
		}
		
		try {
			Properties adaptorsProperties = readProperties("/agiledev/query-parameters-db-adaptors.properties");
			if (adaptorsProperties == null) {
				throw new RuntimeException("Null adaptors properties.");
			}
			
			adaptors = new HashMap<String, QueryParametersDbAdaptor>();
			for (Entry<Object, Object> entry : adaptorsProperties.entrySet()) {
				QueryParametersDbAdaptor adaptor = createAdaptor((String)entry.getValue());
				if (adaptor != null) {
					adaptors.put((String)entry.getKey(), adaptor);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("can't init query parameters db adaptors.");
		}
		
	}
	
	private QueryParametersDbAdaptor createAdaptor(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			if (clazz != null) {
				return (QueryParametersDbAdaptor)clazz.newInstance();
			}
			
			return null;
		} catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn(String.format("Can't create query parameters db adaptor: %s", className));
			}
			return null;
		}
	}

	private Properties readProperties(String resourcePath) throws IOException {
		URL url = QueryParametersInterceptor.class.getResource(resourcePath);
		if (url == null) {
			return null;
		}
		
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = url.openStream();
			properties.load(in);
			
			return properties;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}

}
