package com.isoftstone.agiledev.core.internal;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceRegistration;

import com.isoftstone.agiledev.core.config.ConfigManager;
import com.isoftstone.agiledev.core.config.ConfigsManager;
import com.isoftstone.agiledev.core.config.DefaultConfigsManager;

public class BundleActivator implements org.osgi.framework.BundleActivator, BundleListener {
	private static final String KEY_CONFIGS_MANAGER = "Configs-Manager";
	private Map<Long, List<ServiceRegistration<?>>> bundleIdAndConfigManagerServices;
	
	@Override
	public void start(BundleContext context) throws Exception {
		bundleIdAndConfigManagerServices = new HashMap<Long, List<ServiceRegistration<?>>>();
		
		context.addBundleListener(this);
		registerActivatedConfigs(context);
	}

	private void registerActivatedConfigs(BundleContext context) {
		for (Bundle bundle : context.getBundles()) {
			if (bundle.getState() == Bundle.ACTIVE) {
				registerConfigsIfExisting(bundle);
			}
		}
	}

	private void registerConfigsIfExisting(Bundle bundle) {
		String configsManagerProperties = bundle.getHeaders().get(KEY_CONFIGS_MANAGER);
		if (configsManagerProperties == null)
			return;
		
		StringTokenizer commaTokenizer = new StringTokenizer(configsManagerProperties, ",");
		Class<? extends ConfigsManager> clazz = DefaultConfigsManager.class;
		String[] parameters = null;
		while (commaTokenizer.hasMoreTokens()) {
			String property = commaTokenizer.nextToken();
			if (property.length() < 3) {
				throw new RuntimeException(String.format("illegal property[%s]", property));
			}
			
			int equalIndex = property.indexOf('=');
			if (equalIndex == -1 || equalIndex == 0 || equalIndex == (property.length() - 1)) {
				throw new RuntimeException(String.format("can't read config manager property[%s]", property));
			}
			
			String propertyName = property.substring(0, equalIndex);
			String propertyValue = property.substring(equalIndex + 1);
			
			
			if ("class-name".equals(propertyName)) {
				clazz = getConfigsManagerClass(bundle, propertyValue);
			} else if ("parameters".equals(propertyName)) {
				parameters = getConfigsManagerParameters(propertyValue);
			} else {
				throw new RuntimeException(String.format("Illegal property[%s]", property));
			}
		}
		
		ConfigsManager configsManager = null;
		try {
			Constructor<? extends ConfigsManager> cmConstructor = clazz.getConstructor();
			configsManager = cmConstructor.newInstance();
			configsManager.setParameters(parameters);
		} catch (Exception e) {
			throw new RuntimeException("can't create configs manager", e);
		}
		
		Map<String, ConfigManager> configManagers = configsManager.getConfigManagers();
		Iterator<Entry<String, ConfigManager>> iter = configManagers.entrySet().iterator();
		List<ServiceRegistration<?>> configManagerServices = new ArrayList<ServiceRegistration<?>>();
		while (iter.hasNext()) {
			Entry<String, ConfigManager> entry = iter.next();
			ServiceRegistration<?> sr = bundle.getBundleContext().registerService(ConfigManager.class, entry.getValue(),
					getConfigManagerProperties(entry.getKey()));
			configManagerServices.add(sr);
		}
		
		bundleIdAndConfigManagerServices.put(new Long(bundle.getBundleId()), configManagerServices);
		
	}

	private Dictionary<String, ?> getConfigManagerProperties(String configDomain) {
		Dictionary<String, String> properties = new Hashtable<String, String>();
		properties.put("config.domain", configDomain);
		
		return properties;
	}

	private String[] getConfigsManagerParameters(String sParameters) {
		if (sParameters == null || "".equals(sParameters))
			return null;
		
		StringTokenizer tokenizer = new StringTokenizer(sParameters, ";");
		String[] parameters = new String[tokenizer.countTokens()];
		for (int i = 0; i < tokenizer.countTokens(); i++) {
			parameters[i] = tokenizer.nextToken();
		}
		
		return parameters;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends ConfigsManager> getConfigsManagerClass(Bundle bundle, String className) {
		try {
			Class<?> clazz = bundle.loadClass(className);
			if (ConfigsManager.class.isAssignableFrom(clazz)) {
				return (Class<? extends ConfigsManager>)clazz;
			} else {
				throw new RuntimeException(String.format("%s must implement %s",
					className, ConfigsManager.class.getName()));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(String.format("can't load class %s", className));
		}
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Iterator<Long> iter = bundleIdAndConfigManagerServices.keySet().iterator();
		while (iter.hasNext()) {
			unregisterExistingConfigs(iter.next());
		}
		
		bundleIdAndConfigManagerServices = null;
	}

	@Override
	public void bundleChanged(BundleEvent event) {
		if (event.getType() == BundleEvent.STOPPED) {
			unregisterExistingConfigs(event.getBundle().getBundleId());
		} else if (event.getType() == BundleEvent.STARTED) {
			registerConfigsIfExisting(event.getBundle());
		} else {
			// ignore
		}
	}

	private void unregisterExistingConfigs(long bundleId) {
		List<ServiceRegistration<?>> srs = bundleIdAndConfigManagerServices.get(bundleId);
		for (ServiceRegistration<?> sr : srs) {
			sr.unregister();
		}
	}

}
