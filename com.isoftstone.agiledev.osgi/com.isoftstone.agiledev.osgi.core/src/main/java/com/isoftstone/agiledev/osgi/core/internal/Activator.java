package com.isoftstone.agiledev.osgi.core.internal;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.framework.*;
import org.osgi.service.http.HttpService;

import com.isoftstone.agiledev.osgi.core.http.ActionServlet;
import com.isoftstone.agiledev.osgi.core.web.result.*;

/**
 * Extension of the default OSGi bundle activator
 */
public final class Activator implements BundleActivator,ServiceListener{
	/**
	 * Called whenever the OSGi framework starts our bundle
	 */
	private BundleContext context = null;
	private ActionServlet actionServlet = null;
	private ServiceReference<HttpService> ref = null;
	
	public void start(BundleContext bc) throws Exception {
		System.out.println("STARTING com.isoftstone.agiledev.osgi.core");

		this.context = bc;
		actionServlet = new ActionServlet(bc);
		
		Dictionary props = new Properties();
		props.put("result", "json");
		context.registerService(Result.class.getName(), new JSONResult(), props);

		props = new Properties();
		props.put("result", "gridjson");
		context.registerService(Result.class.getName(), new GridJSONResult(), props);
		
		props = new Properties();
		props.put("result", "stream");
		context.registerService(Result.class.getName(), new StreamResult(), props);
		
		this.registerServlet();
		
		
		ServiceReference<HttpService> sf = (ServiceReference<HttpService>) bc.getServiceReference(HttpService.class.getName());
	}

	/**
	 * Called whenever the OSGi framework stops our bundle
	 */
	public void stop(BundleContext bc) throws Exception {
		System.out.println("STOPPING com.isoftstone.agiledev.osgi.core");

		ServiceReference sf = bc.getServiceReference(Result.class.getName());
		bc.ungetService(sf);
		this.unregisterServlet();
	}

	@Override
	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()){
	        case ServiceEvent.REGISTERED:
        		registerServlet();
	            break;
	
	        case ServiceEvent.UNREGISTERING:
	            unregisterServlet();
	            break;
	    }

	}
	
	private void registerServlet(){
		if (ref == null) {
			ref = (ServiceReference<HttpService>) context.getServiceReference(HttpService.class.getName());
		}
		if (ref != null) {
			try {
				HttpService http = (HttpService) context.getService(ref);
				if (null != http) {
					http.registerServlet("/agile/controller", actionServlet, null, null);
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
	                http.unregister("/agile/controller");
                }
            }catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}