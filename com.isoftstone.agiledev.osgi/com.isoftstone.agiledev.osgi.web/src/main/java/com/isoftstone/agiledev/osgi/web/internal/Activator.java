package com.isoftstone.agiledev.osgi.web.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator implements BundleActivator {
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		System.out.println("STARTING com.isoftstone.agiledev.osgi.web");

		Dictionary props = new Properties();
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		System.out.println("STOPPING com.isoftstone.agiledev.osgi.web");

		// no need to unregister our service - the OSGi framework handles it for
		// us
	}
}
