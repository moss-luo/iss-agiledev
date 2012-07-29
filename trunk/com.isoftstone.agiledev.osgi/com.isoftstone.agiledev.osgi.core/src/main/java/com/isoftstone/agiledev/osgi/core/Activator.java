package com.isoftstone.agiledev.osgi.core;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public interface Activator extends BundleActivator {
	void init();
	void register()throws Exception;
	void unregister()throws Exception;
	
}
