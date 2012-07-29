package com.isoftstone.agiledev.osgi.login.internal;

import org.osgi.service.http.NamespaceException;

import com.isoftstone.agiledev.osgi.core.action.DefaultWebActivator;
import com.isoftstone.agiledev.osgi.core.register.ResourcesRegister;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator extends DefaultWebActivator{
	
	@Override
	protected void registerResources(ResourcesRegister register)
			throws NamespaceException {
		register.registerResources("/web", "web");
	}
	
}
