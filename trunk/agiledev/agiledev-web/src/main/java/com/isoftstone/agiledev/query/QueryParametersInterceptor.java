package com.isoftstone.agiledev.query;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class QueryParametersInterceptor extends MethodFilterInterceptor {
	private static final String DEFAULT_VIEW_FRAMEWORK = "easyui";
//	private String viewFramework = DEFAULT_VIEW_FRAMEWORK;
	
	private List<QueryParametersReader> queryParametersReaders;
	private List<QueryParametersInjector> queryParametersInjectors;
	@Override
	public void init() {
		initQueryParametersHandlers();
		super.init();
	}
	
	private void initQueryParametersHandlers() {
		Properties properties = readProperties("/agiledev/query-parameters-handlers.properties");
		if (properties == null)
			return;
		
		queryParametersInjectors = new ArrayList<QueryParametersInjector>();		
		queryParametersReaders = new ArrayList<QueryParametersReader>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			String handlerName = (String)entry.getKey();
			if (handlerName.endsWith("-injector")) {
				QueryParametersInjector injector = createQueryParametersHandler(entry.getValue(), handlerName);
				if (injector != null) {
					injector.init(readProperties("/agiledev/" + (String)entry.getValue() + ".properties"));
					queryParametersInjectors.add(injector);
				}
			//} else if (handlerName.equals(viewFramework + "-reader")) {
			}else if(handlerName.endsWith("-reader")){
				QueryParametersReader reader = createQueryParametersHandler(entry.getValue(), handlerName);
				if (reader != null) {
					reader.init(readProperties("/agiledev/" + (String)entry.getValue() + "-config.properties"));
				}
				queryParametersReaders.add(reader);
			} else {
				continue;
			}					
		}
	}
	
	private Properties readProperties(String resourcePath) {
		URL url = QueryParametersInterceptor.class.getResource(resourcePath);
		if (url == null)
			return null;
		
		InputStream in = null;
		Properties properties = new Properties();
		try {
			in = url.openStream();
			properties.load(in);
			
			return properties;
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}

	private <T> T createQueryParametersHandler(Object className, String handlerName) {
		String handlerClassName = (String)className;
		try {
			Class<?> handlerClass = Class.forName(handlerClassName);
			@SuppressWarnings("unchecked")
			T handler = (T)handlerClass.newInstance();
			
			return handler;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		QueryParametersReader queryParametersReader = null;
		
		if(queryParametersReaders.size()>0){
			String version = (String) ServletActionContext.getRequest().getSession().getAttribute("version");
			if(version == null || "".equals(version))
				version = DEFAULT_VIEW_FRAMEWORK;
			for (QueryParametersReader q : queryParametersReaders) {
				if(q.getClass().getName().contains(version)){
					queryParametersReader = q;
					break;
				}
			}
		}
		
		if (queryParametersReader != null) {
			Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
			QueryParameters queryParameters = queryParametersReader.readQueryParameters(parameters);
			
			if (queryParameters != null) {
				for (QueryParametersInjector injector : queryParametersInjectors) {
					injector.inject(invocation, queryParameters);
				}
			}
		}
		
		return invocation.invoke();
	}
	
//	public void setViewFramework(String viewFramework) {
//		this.viewFramework = viewFramework;
//	}
}
