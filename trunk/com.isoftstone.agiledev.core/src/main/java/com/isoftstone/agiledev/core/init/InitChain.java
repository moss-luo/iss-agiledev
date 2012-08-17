package com.isoftstone.agiledev.core.init;

import javax.servlet.http.HttpServletRequest;

public interface InitChain {

	void doInit(HttpServletRequest request,InitializeModel model);
}
