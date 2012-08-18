package com.isoftstone.agiledev.core.init;

import javax.servlet.http.HttpServletRequest;

public class DefaultInitChain implements InitChain {

	
	public DefaultInitChain() {
		super();
	}

	public DefaultInitChain(InitializeAdaptor initializeAdaptor) {
		super();
		this.initializeAdaptor = initializeAdaptor;
	}

	private InitializeAdaptor initializeAdaptor = null;
	
	public void setInitializeable(InitializeAdaptor initializeAdaptor) {
		this.initializeAdaptor = initializeAdaptor;
	}

	@Override
	public void doInit(HttpServletRequest request,InitializeModel model) {
		if(initializeAdaptor!=null){
			initializeAdaptor.doInit(request, model);
			initializeAdaptor = null;
		}
	}

}
