package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;
import java.util.Dictionary;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.web.annotation.Action;

public class ActionRegister extends DefaultAnnotationServiceRegister implements AnnotationServiceRegister{


	private Logger logger = LoggerFactory.getLogger(ActionRegister.class);
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start() throws Exception {
		try {
			for (Class<?> clazz : this.getClasses()) {
				Action annotation = (Action) clazz.getAnnotation(Action.class);
				Object o = clazz.newInstance();
				if (o instanceof com.isoftstone.agiledev.osgi.core.web.Action) {
					Dictionary props = new Properties();
					props.put("path", annotation.packageName() + "/"+ annotation.path());
					
					o = this.injecter.injectBean(o);
					this.context.registerService(com.isoftstone.agiledev.osgi.core.web.Action.class.getName(), o,props);
					logger.info("register Action:" + annotation.packageName()+ "/" + annotation.path());
				} else {
					throw new Exception(
							"class "
									+ clazz
									+ " is not an instance of the service class com.isoftstone.mgt.console.core.web.Action");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws Exception {
		for (Class<?> clazz : this.getClasses()) {
			Action annotation = (Action) clazz.getAnnotation(Action.class);
//			ServiceReference<com.isoftstone.agiledev.osgi.core.web.Action> sf = 
//						(ServiceReference<com.isoftstone.agiledev.osgi.core.web.Action>) 
//							this.context.getServiceReferences(com.isoftstone.agiledev.osgi.core.web.Action.class.getName(),
//									"(path="+annotation.packageName() + "/"+ annotation.path()+")")[0];
//			
//			this.context.ungetService(sf);
			this.context.unregisterService(com.isoftstone.agiledev.osgi.core.web.Action.class.getName(),
					"(path="+annotation.packageName() + "/"+ annotation.path()+")");
		}
	}


	@Override	
	public Class<? extends Annotation> getAnnotationType() {
		return Action.class;
	}

}
