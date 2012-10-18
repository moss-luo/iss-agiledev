package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.HashMap;
import java.util.Map;

import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptorProvider;

public class EasyUIDataOutputAdaptorProvider implements DataOutputAdaptorProvider{

	private Map<String,DataOutputAdaptor> adaptors = new HashMap<String,DataOutputAdaptor>();
	@Override
	public void init() {
		DataOutputAdaptor adaptor = new EasyUIGridDataOutputAdaptor();
		adaptors.put(adaptor.getType(), adaptor);
		adaptor = new EasyUIInitOutputAdaptor();
		adaptors.put(adaptor.getType(), adaptor);
		adaptor = new EasyUITreeDataOutputAdaptor();
		adaptors.put(adaptor.getType(), adaptor);
	}

	@Override
	public DataOutputAdaptor getAdaptor(String type) {
		return this.adaptors.get(type);
	}

	
}
