package com.isoftstone.agiledev.osgi.basedata.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.isoftstone.agiledev.osgi.core.action.DefaultConsoleActivator;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator extends DefaultConsoleActivator {
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void startBundle(BundleContext bc) throws Exception {
		System.out.println("STARTING com.isoftstone.agiledev.osgi.basedata");
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stopBundle(BundleContext bc) throws Exception {
		System.out.println("STOPPING com.isoftstone.agiledev.osgi.basedata");
	}
}
