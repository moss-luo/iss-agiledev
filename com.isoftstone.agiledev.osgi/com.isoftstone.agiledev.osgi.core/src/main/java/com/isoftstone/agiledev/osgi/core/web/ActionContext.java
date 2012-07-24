package com.isoftstone.agiledev.osgi.core.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;

public class ActionContext {
	private static ServletRequest request;
	private static ServletResponse response;
	private static BundleContext context;
	public static HttpSession getSession(){
		HttpServletRequest req = (HttpServletRequest) request;
		return req.getSession();
	}
	public static BundleContext getBundleContext() {
		return context;
	}
	public static void setBundleContext(BundleContext context) {
		ActionContext.context = context;
	}
	public static void setRequest(ServletRequest request){
		ActionContext.request = request;
	}
	public static void setResponse(ServletResponse response){
		ActionContext.response = response;
	}
	public static ServletRequest getRequest(){
		return ActionContext.request;
	}
	public static ServletResponse getResponse(){
		return ActionContext.response;
	}
}
