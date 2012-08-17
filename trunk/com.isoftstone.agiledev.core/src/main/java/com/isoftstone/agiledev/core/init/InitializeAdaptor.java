package com.isoftstone.agiledev.core.init;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface InitializeAdaptor {
	public List<? extends InitField> doInit(HttpServletRequest request,InitializeModel model,InitChain initChain);

}
