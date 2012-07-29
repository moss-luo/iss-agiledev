package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;
import java.util.Dictionary;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.service.Service;


public class ServiceRegister extends DefaultAnnotationServiceRegister implements
		AnnotationServiceRegister {

	private Logger logger = LoggerFactory.getLogger(ServiceRegister.class);
	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return Service.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void start() throws Exception {

		for (Class<?> clazz : this.getAnnotationClasses()) {
			Service annotation = (Service) clazz.getAnnotation(Service.class);
			Class<?> superClazz = annotation.type();
			if (superClazz != null && superClazz != Object.class) {
				Class<?>[] parents = clazz.getInterfaces();
				for (Class<?> c : parents) {
					if (c.getName().equals(superClazz.getName())) {
						superClazz = c;
					}
				}
			} else if (clazz.getInterfaces().length != 0) {
				Class<?>[] parents = clazz.getInterfaces();
				if (parents != null && parents.length >= 1) {
					superClazz = parents[0];
				}
			} else {
				logger.error("service [" + clazz.getName()
						+ "] as least implement an interface");
				throw new Exception("service [" + clazz.getName()
						+ "] as least implement an interface");
			}

			String name = annotation.name();
			Dictionary props = new Properties();
			if (name != null && !"".equals(name)) {
				props.put("serviceName", name);
			} else {
				props.put("serviceName", clazz.getName());
			}
			Object o = clazz.newInstance();
			o = this.injecter.injectBean(o);
			this.context.registerService(superClazz.getName(), o,props);
		
			logger.info("register service:[" + clazz.getName() + "]");
		}
	}

	@Override
	public void stop() throws Exception {

	}

}
