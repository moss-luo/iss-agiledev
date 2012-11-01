package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;

import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptorProvider;

public class EasyUIDataOutputAdaptorProvider implements DataOutputAdaptorProvider{

	private Map<String,DataOutputAdaptor> adaptors = new HashMap<String,DataOutputAdaptor>();

	private BundleContext bundleContext = null;
	public EasyUIDataOutputAdaptorProvider() {
	}
	public EasyUIDataOutputAdaptorProvider(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	
	@Override
	public void init() {
		DataOutputAdaptor adaptor = new EasyUIGridDataOutputAdaptor();
		adaptors.put(adaptor.getType(), adaptor);
		adaptor = new EasyUIInitOutputAdaptor(bundleContext);
		adaptors.put(adaptor.getType(), adaptor);
		adaptor = new EasyUITreeDataOutputAdaptor();
		adaptors.put(adaptor.getType(), adaptor);
	}

	@Override
	public DataOutputAdaptor getAdaptor(String type) {
		return this.adaptors.get(type);
	}

	
}
