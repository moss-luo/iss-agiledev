package com.isoftstone.agiledev.web.springmvc.easyui;

import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptorProvider;
import com.isoftstone.agiledev.web.springmvc.DefaultView;

public class EasyUIView extends DefaultView {
	@Override
	public DataOutputAdaptorProvider getDataOutputAdaptorProvider() {
		EasyUIDataOutputAdaptorProvider  provider = new EasyUIDataOutputAdaptorProvider();
		provider.init();
		return provider;

	}
}