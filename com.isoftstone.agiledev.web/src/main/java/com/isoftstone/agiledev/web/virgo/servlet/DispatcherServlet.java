package com.isoftstone.agiledev.web.virgo.servlet;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.gemini.blueprint.context.ConfigurableOsgiBundleApplicationContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.Validator;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;

import com.isoftstone.agiledev.core.ObjectFactory;
import com.isoftstone.agiledev.core.ObjectFactoryAware;
import com.isoftstone.agiledev.core.config.ConfigManager;
import com.isoftstone.agiledev.web.springmvc.binding.BindingCallback;
import com.isoftstone.agiledev.web.springmvc.binding.ServletRequestDataBinder;

@SuppressWarnings("serial")
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet implements ObjectFactory {
	private Map<Class<?>, Map<String, Object>> objProperties = new HashMap<Class<?>, Map<String, Object>>();
	private static final String CONFIG_DOMAIN_OBJECT_PROPERTIES = "object-properties";
	private static final String CONFIG_DOMAIN_BINDING_CALLBACK = "binding-callback";
	private Map<String, ConfigManager> configManagers = new HashMap<String, ConfigManager>();
	private Map<String, Object> objects = new HashMap<String, Object>();
	private List<BindingCallback> bindingCallbacks = new ArrayList<BindingCallback>();
	
	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		String contextClass = config.getInitParameter("context-class");
		if (contextClass != null) {
			try {
				setContextClass(Class.forName(contextClass));
			} catch (ClassNotFoundException e) {
				throw new ServletException(String.format("Context class %s not found", contextClass), e);
			}
		}
		
		super.init(config);
		
		String attrName = getServletContextAttributeName();
		WebApplicationContext wac = (WebApplicationContext)getServletContext().getAttribute(attrName);
		if (wac instanceof ConfigurableOsgiBundleApplicationContext) {
			try {
				BundleContext bc = ((ConfigurableOsgiBundleApplicationContext)wac).getBundleContext();
				objects.put("bundle", bc.getBundle());
				Collection<ServiceReference<ConfigManager>> srs = bc.getServiceReferences(ConfigManager.class, null);
				Iterator<ServiceReference<ConfigManager>> iter = srs.iterator();
				while (iter.hasNext()) {
					ServiceReference<ConfigManager> sr = iter.next();
					ConfigManager cm = bc.getService(sr);
					if (CONFIG_DOMAIN_OBJECT_PROPERTIES.equals(cm.getDomain()) ||
							CONFIG_DOMAIN_BINDING_CALLBACK.equals(cm.getDomain())) {
						configManagers.put(cm.getDomain(), cm);
					}
				}
			} catch (InvalidSyntaxException e) {
				// it's impossible to run to here
			}
		}
		
		initObjProperties();
		initBindingCallbacks();
	}
	
	private void initObjProperties() {
		ConfigManager configManager = getConfigManager(CONFIG_DOMAIN_OBJECT_PROPERTIES);
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
	
	private void initBindingCallbacks() {
		bindingCallbacks = new ArrayList<BindingCallback>();
		ConfigManager cbConfigManager = getConfigManager(CONFIG_DOMAIN_BINDING_CALLBACK);
		Bundle bundle = (Bundle)get("bundle");
		for (String sCallbackClass : cbConfigManager.keys()) {
			String bundleSymbolicName = (String)cbConfigManager.get(sCallbackClass);
			Bundle host = null;
			if (!"".equals(bundleSymbolicName)) {
				host = findHostBundle(bundle, bundleSymbolicName);
			}
			
			try {
				Class<?> callbackClass = null;
				if (host == null) {
					callbackClass = Class.forName(sCallbackClass);
				} else {
					callbackClass = host.loadClass(sCallbackClass);
				}
				
				if (BindingCallback.class.isAssignableFrom(callbackClass)) {
					BindingCallback bc = (BindingCallback)callbackClass.newInstance();
					if (ObjectFactoryAware.class.isAssignableFrom(callbackClass)) {
						((ObjectFactoryAware)bc).setObjectFactory(this);
					}
					bindingCallbacks.add(bc);
				} else {
					throw new RuntimeException(String.format("class[%s] must implement interface[%s]",
							sCallbackClass, BindingCallback.class.getName()));
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(String.format("can't load class[%s]", sCallbackClass));
			} catch (Exception e) {
				throw new RuntimeException(String.format("can't initialize class[%s]", sCallbackClass));
			}
		}
	}
	
	private Bundle findHostBundle(Bundle bundle, String bundleSymbolicName) {
		for (Bundle aBundle : bundle.getBundleContext().getBundles()) {
			if (bundleSymbolicName.equals(aBundle.getSymbolicName()))
				return aBundle;
		}
		
		return null;
	}
	
	private class AnnotationMethodHandlerAdapter extends org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter {		
		@Override
		protected ServletRequestDataBinder createBinder(HttpServletRequest request, Object target,
				String objectName) throws Exception {
			ServletRequestDataBinder dataBinder = new ServletRequestDataBinder(bindingCallbacks, target, objectName);
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

	@Override
	public Map<String, ConfigManager> getConfigManagers() {
		return Collections.unmodifiableMap(configManagers);
	}

	@Override
	public ConfigManager getConfigManager(String configDomain) {
		return configManagers.get(configDomain);
	}

	@Override
	public Object get(String id) {
		return objects.get(id);
	}
}
