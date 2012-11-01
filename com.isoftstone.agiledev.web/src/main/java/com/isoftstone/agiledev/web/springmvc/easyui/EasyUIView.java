package com.isoftstone.agiledev.web.springmvc.easyui;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.BundleContext;

import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptorProvider;
import com.isoftstone.agiledev.web.springmvc.DefaultView;

public class EasyUIView extends DefaultView implements BundleContextAware{
	private BundleContext bundleContext = null;
	@Override
	public DataOutputAdaptorProvider getDataOutputAdaptorProvider() {
		EasyUIDataOutputAdaptorProvider  provider = new EasyUIDataOutputAdaptorProvider(bundleContext);
		provider.init();
		return provider;

	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
}
