package com.isoftstone.agiledev.osgi.core.inject;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class OSGIClassLoader implements ClassLoader {

	private BundleContext context;

	public void setBundleContext(BundleContext context) {
		this.context = context;
	}

	@Override
	public Object loadClass(String name) {
		return this.loadClass(name, null);
	}

	public Object loadClass(String name, Map<String, String> condition) {

		try {
			ServiceReference<?> sf = null;
			if(condition!=null && !condition.entrySet().isEmpty()){
				ServiceReference<?>[] sfs = this.context.getServiceReferences(name,
						"(" + condition.entrySet().iterator().next().getKey() + "="
								+ condition.entrySet().iterator().next().getValue()
								+ ")");
				if(sfs!=null && sfs.length>0)
					sf = sfs[0];
			}else{
				sf = this.context.getServiceReference(name);
			}
			
			if(sf!=null){
				return this.context.getService(sf);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
