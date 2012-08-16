package com.isoftstone.agiledev.osgi.core.register;

import java.util.Dictionary;

import javax.servlet.http.HttpServlet;

import org.osgi.service.http.HttpService;

import com.isoftstone.agiledev.osgi.core.Register;

public interface ResourcesRegister extends Register{

	void registerResources(String alias,String name);
	void regiserServlet(String alias,HttpServlet servlet,Dictionary<?,?> dic);
	
	void setHttpService(HttpService httpService);
	void setContextPath(String contextPath);
	
	
//	Map<String,String> getResources();
//	Map<String,Map<HttpServlet,Dictionary<?, ?>>> getServlets();
	
}

