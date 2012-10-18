package com.isoftstone.agiledev.web.springmvc.ligerui;

import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptorProvider;
import com.isoftstone.agiledev.web.springmvc.DefaultView;

public class LigerUIView extends DefaultView{

	@Override
	public DataOutputAdaptorProvider getDataOutputAdaptorProvider() {
		LigerUIDataOutputAdaptorProvider provider = new LigerUIDataOutputAdaptorProvider();
		provider.init();
		return provider;
	}
}
