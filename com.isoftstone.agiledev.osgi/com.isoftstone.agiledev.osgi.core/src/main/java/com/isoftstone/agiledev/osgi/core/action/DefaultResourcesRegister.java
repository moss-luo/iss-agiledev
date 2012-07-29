package com.isoftstone.agiledev.osgi.core.action;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

import org.osgi.service.http.HttpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultResourcesRegister implements Register {

	private Map<String,String> resources = new HashMap<String, String>();
	private Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = new HashMap<String,Map<HttpServlet,Dictionary<?, ?>>>();
	
	private String contextPath = null;
	private HttpService httpService = null;
	
	private Logger logger = LoggerFactory.getLogger(DefaultResourcesRegister.class);
	
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
	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}
	@Override
	public void start() throws Exception {
		for (String k : servlets.keySet()) {
			Map<HttpServlet,Dictionary<?, ?>> temp = servlets.get(k); 
			httpService.registerServlet(k, temp.entrySet().iterator().next().getKey(), temp.entrySet().iterator().next().getValue(), null);
			logger.info("register servlet "+k);
		}
		
		for (String k : resources.keySet()) {
			httpService.registerResources(k, resources.get(k), null);
			logger.info("register resources "+k);
		}
	}
	@Override
	public void stop() throws Exception  {
		for (String k : servlets.keySet()) {
			httpService.unregister(k);
			logger.info("unregister servlet "+k);
		}
		
		for (String k : resources.keySet()) {
			httpService.unregister(k);
			logger.info("unregister resources "+k);
		}
	}

}
