package com.isoftstone.agiledev.osgi.core.action;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.isoftstone.agiledev.osgi.commons.util.AppUtils;

public class DefaultWebActivator extends DefaultConsoleActivator implements ServiceListener{

	private Register register = null;

	
	@Override
	public void startBundle(BundleContext context) throws Exception {
		Properties runtime = AppUtils.getRuntime();
		
		this.register = new DefaultResourcesRegister();
		this.register.setContextPath(runtime.getProperty("webContext"));
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
	
	
	protected void register(){
		try {
			this.register.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			ref = (ServiceReference<HttpService>) this.context.getServiceReference(HttpService.class.getName());
//			if(ref!=null){
//				HttpService http = this.context.getService(ref);
//				if(http!=null){
//					this.registerResources(register);
//					Map<String,String> res = this.register.getResources();
//					for (String k : res.keySet()) {
//						http.registerResources(k, res.get(k), null);
//						logger.info("register resources "+k);
//					}
//					this.registerServlet(register);
//					Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = this.register.getServlets();
//					for (String k : servlets.keySet()) {
//						Map<HttpServlet,Dictionary<?, ?>> temp = servlets.get(k); 
//						http.registerServlet(k, temp.entrySet().iterator().next().getKey(), temp.entrySet().iterator().next().getValue(), null);
//						logger.info("register servlet "+k);
//					}
//				}
//			}
//		} catch (NamespaceException e) {
//			e.printStackTrace();
//		} catch (ServletException e) {
//			e.printStackTrace();
//		}
	}
	
	
	protected void unregister(){
		try {
			this.register.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			ref = (ServiceReference<HttpService>) this.context.getServiceReference(HttpService.class.getName());
//			if(ref!=null){
//				HttpService http = this.context.getService(ref);
//				if(http!=null){
//					Map<String,String> res = this.register.getResources();
//					for (String k : res.keySet()) {
//						http.unregister(k);
//						this.unregistedResources();
//					}
//					Map<String,Map<HttpServlet,Dictionary<?, ?>>> servlets = this.register.getServlets();
//					for (String k : servlets.keySet()) {
//						http.unregister(k);
//						this.unregistedServlet(servlets.get(k).entrySet().iterator().next().getKey());
//					}
//				}
//			}
//		} catch (Exception e) {
//		}
	}
	/**
	 * web application bundle注册资源文件时需要重写
	 * @param register
	 * @throws NamespaceException
	 */
	protected void registerResources(Register register)throws NamespaceException{}
	/**
	 * web application bundle注册自定义Servlet时需要重写
	 * @param register
	 * @throws ServletException
	 */
	protected void registerServlet(Register register)throws ServletException{}
	
	/**
	 * HttpService停止并且卸载当前web application bundle所有servlet后调用
	 * @param servlet
	 */
	protected void unregistedServlet(HttpServlet servlet){}
	/**
	 * HttpService停止并且写在当前web application bundle所有资源后调用
	 */
	protected void unregistedResources(){}
	
}
