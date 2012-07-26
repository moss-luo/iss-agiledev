package com.isoftstone.agiledev.osgi.core.action;

import java.util.Dictionary;
import java.util.Map;

import javax.servlet.http.HttpServlet;

public interface Register {

	void registerResources(String alias,String name);
	void regiserServlet(String alias,HttpServlet servlet,Dictionary<?,?> dic);
	
	void setContextPath(String contextPath);
	
	Map<String,String> getResources();
	Map<String,Map<HttpServlet,Dictionary<?, ?>>> getServlets();
	
}
