package com.isoftstone.agiledev.osgi.core.action;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import com.isoftstone.agiledev.osgi.core.action.Register;

public class ResourcesRegister implements Register {

	private Map<String,String> resources = new HashMap<String, String>();
	private Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = new HashMap<String,Map<HttpServlet,Dictionary<?, ?>>>();
	
	private String contextPath = null;
	
	@Override
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	@Override
	public void registerResources(String alias, String name) {
		this.resources.put("/"+contextPath+alias, name);
	}

	@Override
	public void regiserServlet(String alias, HttpServlet servlet,
			Dictionary<?, ?> dic) {
		Map<HttpServlet, Dictionary<?, ?>> temp = new HashMap<HttpServlet, Dictionary<?,?>>();
		temp.put(servlet, dic);
		this.servlets.put("/"+contextPath+alias, temp);
	}

	@Override
	public Map<String, String> getResources() {
		return this.resources;
	}

	@Override
	public Map<String,Map<HttpServlet,Dictionary<?, ?>>> getServlets() {
		return this.servlets;
	}

}
