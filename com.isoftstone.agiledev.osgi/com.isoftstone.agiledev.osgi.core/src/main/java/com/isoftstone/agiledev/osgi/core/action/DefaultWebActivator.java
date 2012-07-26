package com.isoftstone.agiledev.osgi.core.action;

import java.util.Dictionary;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.commons.util.AppUtils;

public class DefaultWebActivator extends DefaultConsoleActivator implements ServiceListener{

	private ServiceReference<HttpService> ref = null;
	private Register register = null;

	private Logger logger = LoggerFactory
			.getLogger(DefaultWebActivator.class);
	
	@Override
	public void startBundle(BundleContext context) throws Exception {
		Properties runtime = AppUtils.getRuntime();
		
		register = new ResourcesRegister();
		register.setContextPath(runtime.getProperty("webContext"));
		this.register();
		
		context.addServiceListener(this,
				"(objectClass=" + HttpService.class.getName() + ")");
	}
	@Override
	protected void stopBundle(BundleContext arg0) throws Exception {
		this.unregister();
	}
	
	
	@Override
	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()) {
		case ServiceEvent.REGISTERED:
			this.register();
			break;

		case ServiceEvent.UNREGISTERING:
			this.unregister();
			break;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void register(){
		try {
			ref = (ServiceReference<HttpService>) this.context.getServiceReference(HttpService.class.getName());
			if(ref!=null){
				HttpService http = this.context.getService(ref);
				if(http!=null){
					this.registerResources(register);
					Map<String,String> res = this.register.getResources();
					for (String k : res.keySet()) {
						http.registerResources(k, res.get(k), null);
						logger.info("register resources "+k);
					}
					this.registerServlet(register);
					Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = this.register.getServlets();
					for (String k : servlets.keySet()) {
						Map<HttpServlet,Dictionary<?, ?>> temp = servlets.get(k); 
						http.registerServlet(k, temp.entrySet().iterator().next().getKey(), temp.entrySet().iterator().next().getValue(), null);
						logger.info("register servlet "+k);
					}
				}
			}
		} catch (NamespaceException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	protected void unregister(){
		try {
			ref = (ServiceReference<HttpService>) this.context.getServiceReference(HttpService.class.getName());
			if(ref!=null){
				HttpService http = this.context.getService(ref);
				if(http!=null){
					Map<String,String> res = this.register.getResources();
					for (String k : res.keySet()) {
						http.unregister(k);
					}
					Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = this.register.getServlets();
					for (String k : servlets.keySet()) {
						http.unregister(k);
					}
				}
			}
		} catch (Exception e) {
		}
	}
	
	protected void registerResources(Register register)throws NamespaceException{}
	protected void registerServlet(Register register)throws ServletException{}
	
	
	protected void unregisterServlet(){}
	protected void unregisterResources(){}
	
}
