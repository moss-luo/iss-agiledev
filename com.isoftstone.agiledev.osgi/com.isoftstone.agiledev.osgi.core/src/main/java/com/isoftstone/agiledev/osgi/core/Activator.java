package com.isoftstone.agiledev.osgi.core;

import org.osgi.framework.BundleActivator;

public interface Activator extends BundleActivator {
	void init();
	void register()throws Exception;
	void unregister()throws Exception;
	
}
