package com.isoftstone.agiledev.osgi.core.action;

import java.util.Dictionary;

import javax.servlet.http.HttpServlet;

import org.osgi.service.http.HttpService;

public interface ResourcesRegister {

	void registerResources(String alias,String name);
	void regiserServlet(String alias,HttpServlet servlet,Dictionary<?,?> dic);
	
	void setHttpService(HttpService httpService);
	void setContextPath(String contextPath);
	
	void start() throws Exception ;
	void stop() throws Exception ;
	
//	Map<String,String> getResources();
//	Map<String,Map<HttpServlet,Dictionary<?, ?>>> getServlets();
	
}

