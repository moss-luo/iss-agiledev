package com.isoftstone.agiledev.osgi.db.internal;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.isoftstone.agiledev.osgi.db.dao.*;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator implements BundleActivator {
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	public void start(BundleContext bc) throws Exception {
		System.out.println("STARTING com.isoftstone.agiledev.osgi.db");


		SessionCreater creater = new SessionFactory();
		
		bc.registerService(SessionCreater.class.getName(), creater, null);
		bc.registerService(IBaseDao.class.getName(), new BaseDao(creater), null);
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		System.out.println("STOPPING com.isoftstone.agiledev.osgi.db");
	}
}
