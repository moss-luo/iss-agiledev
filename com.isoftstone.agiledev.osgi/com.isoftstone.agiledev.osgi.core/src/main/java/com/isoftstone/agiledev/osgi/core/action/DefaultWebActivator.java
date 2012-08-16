package com.isoftstone.agiledev.osgi.core.action;

import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;

import com.isoftstone.agiledev.osgi.commons.util.AppUtils;
import com.isoftstone.agiledev.osgi.core.register.DefaultResourcesRegister;
import com.isoftstone.agiledev.osgi.core.register.ResourcesRegister;

public abstract class DefaultWebActivator extends DefaultActivator implements ServiceListener{

	private ResourcesRegister register = null;

	
	@Override
	public void startBundle(BundleContext context) throws Exception {
		Properties runtime = AppUtils.getRuntime();
		
		this.register = new DefaultResourcesRegister();
		this.register.setContextPath(runtime.getProperty("webContext"));
		this.registerResources();
		
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
			try {
				this.registerResources();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		case ServiceEvent.UNREGISTERING:
			try {
				this.unregisterResources();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void registerResources()throws Exception{
		this.registerResources(register);
		
		ServiceReference<HttpService> ref = (ServiceReference<HttpService>) this.context.getServiceReference(HttpService.class.getName());
		if(ref!=null){
			HttpService http = this.context.getService(ref);
			this.register.setHttpService(http);
			this.register.start();
		}
	}
	

	private void unregisterResources()throws Exception{
		this.register.stop();
		this.unregistedResources(register);
	}
	/**
	 * web application bundle注册资源文件时需要重写
	 * @param register
	 * @throws NamespaceException
	 */
	protected void registerResources(ResourcesRegister register)throws NamespaceException{}
	/**
	 * HttpService停止并且写在当前web application bundle所有资源后调用
	 */
	protected void unregistedResources(ResourcesRegister register){}
	
	/**
	 * web application bundle注册自定义Servlet时需要重写
	 * @param register
	 * @throws ServletException
	 */
	protected void registerServlet(ResourcesRegister register)throws ServletException{}
	
	/**
	 * HttpService停止并且卸载当前web application bundle所有servlet后调用
	 * @param servlet
	 */
	protected void unregistedServlet(HttpServlet servlet){}
	
}
