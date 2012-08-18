package com.isoftstone.agiledev.core.init;

import javax.servlet.http.HttpServletRequest;

public interface InitializeAdaptor {
	public void doInit(HttpServletRequest request,InitializeModel model);

}
