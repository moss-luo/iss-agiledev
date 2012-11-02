package com.isoftstone.agiledev.web.virgo.servlet;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.io.Resource;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.HandlerAdapter;

import com.isoftstone.agiledev.core.ConfigManager;
import com.isoftstone.agiledev.core.ConfigManagerImpl;
import com.isoftstone.agiledev.core.ObjectFactory;
import com.isoftstone.agiledev.web.springmvc.binding.ServletRequestDataBinder;

@SuppressWarnings("serial")
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet implements ObjectFactory {
	private Map<Class<?>, Map<String, Object>> objProperties = new HashMap<Class<?>, Map<String, Object>>();
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String contextClass = config.getInitParameter("contextClass");
		if (contextClass != null) {
			try {
				setContextClass(Class.forName(contextClass));
			} catch (ClassNotFoundException e) {
				throw new ServletException(String.format("Context class %s not found", contextClass), e);
			}
		}
		
		super.init(config);		
		initObjProperties();
	}
	
	private void initObjProperties() {
		Resource[] resources = null;
		try {
			resources = getWebApplicationContext().getResources("classpath:/META-INF/agiledev/agiledev-web.properties");			
		} catch (IOException e) {
			// do nothing
		}
		
		if (resources == null) {
			return;
		}
		
		ConfigManager configManager = new ConfigManagerImpl(resources);
		readObjectProperties(configManager);
	}
	
	private void readObjectProperties(ConfigManager configManager) {
		for (String key : configManager.keys()) {
			String sKey = (String)key;
			int lastDotIndex = sKey.lastIndexOf(".");
			if (lastDotIndex == -1 || lastDotIndex == sKey.length() - 1) {
				continue;
			}
			
			String className = sKey.substring(0, lastDotIndex);
			String propertyName = sKey.substring(lastDotIndex + 1);
			
			try {
				Class<?> clazz = Class.forName(className);
				BeanWrapper bw = new BeanWrapperImpl(clazz);
				PropertyDescriptor pd = bw.getPropertyDescriptor(propertyName);
				Object propertyValue = bw.convertIfNecessary(configManager.get(key), pd.getPropertyType());
				
				Map<String, Object> aObjProperties = objProperties.get(clazz);
				if (aObjProperties == null) {
					aObjProperties = new HashMap<String, Object>();
					objProperties.put(clazz, aObjProperties);
				}
				
				aObjProperties.put(propertyName, propertyValue);
			} catch (Exception e) {
				logger.warn("Can't read object property %s. Exception is %s.", className, propertyName);
			}
		}
	}
	
	@Override
	public <T> T create(Class<T> clazz) {
		try {
			T obj = clazz.newInstance();
			Map<String, Object> properties = objProperties.get(clazz);
			if (properties != null) {
				BeanWrapper bw = new BeanWrapperImpl(clazz);
				for (String propertyName : properties.keySet()) {
					try {
						bw.setPropertyValue(propertyName, properties.get(propertyName));
					} catch (Exception e) {
						throw new RuntimeException(String.format("Can't set property value %s for class %s.",
								propertyName, clazz), e);
					}
				}
			}
			
			return obj;
		} catch (Exception e) {
			throw new RuntimeException(String.format("Can't initialize class %s", clazz), e);
		}
	}
	
	@Override
	protected HandlerAdapter getHandlerAdapter(Object handler) throws ServletException {
		HandlerAdapter ha = super.getHandlerAdapter(handler);
		if (ha instanceof org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter) {
			return new AnnotationMethodHandlerAdapter();
		}
		
		return ha;
	}
	
	private class AnnotationMethodHandlerAdapter extends org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter {
		@Override
		protected ServletRequestDataBinder createBinder(HttpServletRequest request, Object target,
				String objectName) throws Exception {
			ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(DispatcherServlet.this, target, objectName);
			ApplicationContext appContext = DispatcherServlet.this.getWebApplicationContext();
			if (appContext != null) {
				ConversionService conversionService = null;
				try {
					conversionService = appContext.getBean(ConversionService.class);
				} catch (NoSuchBeanDefinitionException e) {
					// ignore
				}
				
				if (conversionService != null) {
					dataBinder.setConversionService(conversionService);
				}
				
				Validator validator = null;
				try {
					validator = appContext.getBean(Validator.class);
				} catch (NoSuchBeanDefinitionException e) {
					// ignore
				}
				
				if (validator != null) {
					dataBinder.setValidator(validator);
				}
			}
			
			return dataBinder;
		}
	}
}
