package com.isoftstone.agiledev.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface DataOutputAdaptor {

	void output(HttpServletRequest request,HttpServletResponse response,Object obj);
	boolean check(Object o);
	void setType(String type);
	String getType();
}
