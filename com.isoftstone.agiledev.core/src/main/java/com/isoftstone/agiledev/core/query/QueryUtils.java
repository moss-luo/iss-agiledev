package com.isoftstone.agiledev.core.query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class QueryUtils {
	public static <T> T query(QueryManager queryManager, QueryExecutor<T> executor) {
		int page = queryManager.getPage();
		int rows = queryManager.getRows();
		
		if (page == QueryManager.NON_PAGEABLE) {
			page = 1;
			rows = Integer.MAX_VALUE;
		}
		
		int start = (page - 1) * rows;
		int end = page * rows;
		
		String orderBy = null;
		if (queryManager.getSort() != null) {
			orderBy = queryManager.getSort();
			
			if (queryManager.getOrder() != null && "desc".equals(queryManager.getOrder().toLowerCase())) {
				orderBy = String.format("%s %s", orderBy, " DESC");
			} else {
				orderBy = String.format("%s %s", orderBy, " ASC");
			}
		}
		
		return executor.execute(start, end, orderBy);
	}
	
	public static Properties readProperties(String resourcePath) {
		return readProperties(resourcePath, QueryUtils.class.getClassLoader());
	}
	
	public static Properties readProperties(String resourcePath, ClassLoader classLoader) {
		URL url = classLoader.getResource(resourcePath);
		if (url == null) {
			return null;
		}
		
		return readProperties(url);
	}
	
	private static Properties readProperties(URL url) {
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = url.openStream();
			properties.load(in);
			
			return properties;
		} catch (IOException e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}
	
/*	public static Properties readProperties(String resourcePath, boolean multi) {
		return readProperties(resourcePath, multi, QueryUtils.class.getClassLoader());
	}
	
	public static Properties readProperties(String resourcePath, boolean multi, ClassLoader classLoader) {
		if (classLoader == null) {
			classLoader = QueryUtils.class.getClassLoader();
		}
		
		if (multi) {
			Enumeration<URL> urls = null;
			try {
				urls = QueryParametersInterceptor.class.getClassLoader().getResources(resourcePath);
			} catch (IOException e) {
				return null;
			}
			
			if (urls == null) {
				return null;
			}
			
			Properties properties = new Properties();
			while (urls.hasMoreElements()) {
				properties.putAll(readProperties(urls.nextElement()));
			}
			
			return properties;			
		} else {
			return readProperties(resourcePath);			
		}
	}*/
}
