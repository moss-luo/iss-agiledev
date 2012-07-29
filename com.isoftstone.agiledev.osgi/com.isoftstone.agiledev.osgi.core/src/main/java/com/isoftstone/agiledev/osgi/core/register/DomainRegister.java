package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.domain.EntityAlias;

public class DomainRegister extends DefaultAnnotationServiceRegister implements AnnotationServiceRegister {

	private Logger logger = LoggerFactory.getLogger(DomainRegister.class);

	@Override
	public void start() throws Exception {
		for (Class<?> clazz : this.getClasses()) {
			EntityAlias annotation = (EntityAlias) clazz.getAnnotation(EntityAlias.class);
			if (annotation.value() != null)
				this.context.registerDomain(annotation.value(), clazz);
			else
				this.context.registerDomain(clazz.getName(), clazz);
			
			logger.info("register domain:[" + clazz.getName() + "]");
		}
		
	}

	@Override
	public void stop() throws Exception {

	}

	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return EntityAlias.class;
	}
}
