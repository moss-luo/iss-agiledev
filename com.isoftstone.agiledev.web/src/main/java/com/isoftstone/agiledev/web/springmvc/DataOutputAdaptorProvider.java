package com.isoftstone.agiledev.web.springmvc;

public interface DataOutputAdaptorProvider {

	void init();
	DataOutputAdaptor getAdaptor(String type);
}
