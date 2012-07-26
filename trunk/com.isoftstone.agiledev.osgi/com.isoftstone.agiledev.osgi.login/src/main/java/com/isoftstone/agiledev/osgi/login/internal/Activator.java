package com.isoftstone.agiledev.osgi.login.internal;

import org.osgi.service.http.NamespaceException;

import com.isoftstone.agiledev.osgi.core.action.DefaultWebActivator;
import com.isoftstone.agiledev.osgi.core.action.Register;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator extends DefaultWebActivator{
	
	
	@Override
	protected void registerResources(Register register)throws NamespaceException {
		register.registerResources("/web", "web");
	}
	
//	/**
//	 * Called whenever the OSGi framework starts our bundle
//	 */
//	private BundleContext context = null;
//	private ServiceReference<HttpService> ref = null;
//	private String contextPath = null;
//	public void startBundle(BundleContext bc) throws Exception {
//		System.out.println("STARTING com.isoftstone.agiledev.osgi.login");
//		this.context = bc;
//
//
//		Properties runtime = AppUtils.getRuntime();
//		contextPath = runtime.getProperty("webContext");
////		Dictionary props = new Properties();
//		
//		registerResources();
//		
//		bc.addServiceListener(this,"(objectClass=" + HttpService.class.getName() + ")");
//	}
//
//	/**
//	 * Called whenever the OSGi framework stops our bundle
//	 */
//	public void stopBundle(BundleContext bc) throws Exception {
//		System.out.println("STOPPING com.isoftstone.agiledev.osgi.login");
//
//	}
//	
//
//
//	public void serviceChanged(ServiceEvent event) {
//		switch (event.getType()){
//	        case ServiceEvent.REGISTERED:
//	        	registerResources();
//	            break;
//	
//	        case ServiceEvent.UNREGISTERING:
//	            unregisterResources();
//	            break;
//	    }
//
//	}
//	
//
//	private void registerResources() {
//		if (ref == null) {
//			ref = (ServiceReference<HttpService>) context.getServiceReference(HttpService.class.getName());
//		}
//		if (ref != null) {
//			try {
//				HttpService http = (HttpService) context.getService(ref);
//				if (null != http) {
//					
//
//					http.registerResources("/"+contextPath+"/web", "web", null);
//					
//					
//					System.out.println("REGISTED static files http://localhost:8080/agile/web/login.html");
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//	}
//	
//
//	private void unregisterResources(){
//		if (ref != null) {
//            try {
//                HttpService http = (HttpService) context.getService(ref);
//                if(null != http){
//
//	                http.unregister("/"+contextPath+"/web");
//                }
//            }catch(Exception e){
//            	e.printStackTrace();
//            }
//        }
//	}
}
