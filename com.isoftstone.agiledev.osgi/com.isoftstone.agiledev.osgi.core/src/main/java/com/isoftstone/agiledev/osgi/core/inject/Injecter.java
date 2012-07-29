package com.isoftstone.agiledev.osgi.core.inject;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

public class Injecter {
	private ClassLoader classLoader = null;
	
	public void setClassLoader(ClassLoader classLoader){
		this.classLoader = classLoader;
	}
	public Object injectBean(Object o){

		try {
			Class<?> clazz = o.getClass();
			for (Field f : clazz.getDeclaredFields()) {
				boolean oldAccess = f.isAccessible();
				f.setAccessible(true);
				if (f.isAnnotationPresent(Resource.class)) {
					Resource resource = f.getAnnotation(Resource.class);
					String serviceName = resource.name();

//					ServiceReference sf = null;
//					if (serviceName != null && !"".equals(serviceName)) {
//						ServiceReference[] sfs = this.context
//								.getServiceReferences(f.getType().getName(),
//										"(serviceName=" + serviceName + ")");
//						if (sfs != null && sfs.length > 0)
//							sf = sfs[0];
//					} else {
//						sf = this.context.getServiceReference(f.getType().getName());
//					}
//					if (sf != null) {
//						Object serviceInstance = this.context.getService(sf);

					Object serviceInstance = null;
					
					if(serviceName!=null && !"".equals(serviceName)){
						Map<String, String> condition = new HashMap<String,String>();
						condition.put("serviceName", serviceName);
						serviceInstance = this.classLoader.loadClass(f.getType().getName(), condition);
						
					}else{
						serviceInstance = this.classLoader.loadClass(f.getType().getName());
					}
					serviceInstance = this.injectBean(serviceInstance);
					f.set(o, serviceInstance);
//					}
				}
				f.setAccessible(oldAccess);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return o;
	}

}
