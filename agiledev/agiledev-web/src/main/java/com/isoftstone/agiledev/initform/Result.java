package com.isoftstone.agiledev.initform;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;

public interface Result {

	void doResult(HttpServletResponse response,ActionInvocation action);
}
