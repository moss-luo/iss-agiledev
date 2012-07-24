package com.isoftstone.agiledev.osgi.login.internal;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpService;

import com.isoftstone.agiledev.osgi.core.action.DefaultConsoleActivator;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator extends DefaultConsoleActivator implements ServiceListener{
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	private BundleContext context = null;
	private ServiceReference<HttpService> ref = null;
	public void startBundle(BundleContext bc) throws Exception {
		System.out.println("STARTING com.isoftstone.agiledev.osgi.login");
		this.context = bc;
		
//		Dictionary props = new Properties();
		
		registerServlet();
		
		bc.addServiceListener(this,"(objectClass=" + HttpService.class.getName() + ")");
		
		ServiceReference<HttpService> sf = (ServiceReference<HttpService>) bc.getServiceReference(HttpService.class.getName());

	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stopBundle(BundleContext bc) throws Exception {
		System.out.println("STOPPING com.isoftstone.agiledev.osgi.login");

	}
	


	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()){
	        case ServiceEvent.REGISTERED:
        		registerServlet();
//        		registerDomain();
//        		registerMapper();
	            break;
	
	        case ServiceEvent.UNREGISTERING:
	            unregisterServlet();
	            break;
	    }

	}
	

	private void registerServlet() {
		if (ref == null) {
			ref = (ServiceReference<HttpService>) context.getServiceReference(HttpService.class.getName());
		}
		if (ref != null) {
			try {
				HttpService http = (HttpService) context.getService(ref);
				if (null != http) {
					

					http.registerResources("/agile/web", "web", null);
					http.registerResources("/agile/resources", "resources", null);
					http.registerResources("/agile/resources/agiledev", "resources/agiledev", null);
					http.registerResources("/agile/resources/agiledev/i18n", "resources/agiledev/i18n", null);
					http.registerResources("/agile/resources/agiledev/style", "resources/agiledev/style", null);

					http.registerResources("/agile/resources/images", "resources/images", null);
					http.registerResources("/agile/resources/ligerui/js", "resources/ligerui/js", null);
					http.registerResources("/agile/resources/ligerui/js/core", "resources/ligerui/js/core", null);
					http.registerResources("/agile/resources/ligerui/skins", "resources/ligerui/skins", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua", "resources/ligerui/skins/Aqua", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/css", "resources/ligerui/skins/Aqua/css", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images", "resources/ligerui/skins/Aqua/images", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/common", "resources/ligerui/skins/Aqua/images/common", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/controls", "resources/ligerui/skins/Aqua/images/controls", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/dateeditor", "resources/ligerui/skins/Aqua/images/dateeditor", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/form", "resources/ligerui/skins/Aqua/images/form", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/grid", "resources/ligerui/skins/Aqua/images/grid", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/icon", "resources/ligerui/skins/Aqua/images/icon", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/layout", "resources/ligerui/skins/Aqua/images/layout", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/menu", "resources/ligerui/skins/Aqua/images/menu", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/panel", "resources/ligerui/skins/Aqua/images/panel", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/tree", "resources/ligerui/skins/Aqua/images/tree", null);
					http.registerResources("/agile/resources/ligerui/skins/Aqua/images/win", "resources/ligerui/skins/Aqua/images/win", null);
					
					
					System.out.println("REGISTED static files http://localhost:8080/agile/web/login.html");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	

	private void unregisterServlet(){
		if (ref != null) {
            try {
                HttpService http = (HttpService) context.getService(ref);
                if(null != http){

	                http.unregister("/agile/web");
					http.unregister("/agile/resources");
					http.unregister("/agile/resources/agiledev");
					http.unregister("/agile/resources/agiledev/i18n");
					http.unregister("/agile/resources/agiledev/style");

					http.unregister("/agile/resources/images");
					http.unregister("/agile/resources/ligerui/js");
					http.unregister("/agile/resources/ligerui/js/core");
					http.unregister("/agile/resources/ligerui/skins");
					http.unregister("/agile/resources/ligerui/skins/Aqua");
					http.unregister("/agile/resources/ligerui/skins/Aqua/css");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/common");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/controls");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/dateeditor");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/form");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/grid");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/icon");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/layout");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/menu");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/panel");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/tree");
					http.unregister("/agile/resources/ligerui/skins/Aqua/images/win");
					
                }
            }catch(Exception e){
            	e.printStackTrace();
            }
        }
	}
}
