package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.domain.EntityMapper;

public class MapperRegister extends DefaultAnnotationServiceRegister implements AnnotationServiceRegister{



	private Logger logger = LoggerFactory.getLogger(MapperRegister.class);
	@Override
	public Class<? extends Annotation> getAnnotationType() {
		return EntityMapper.class;
	}
	@SuppressWarnings({ "rawtypes"})
	@Override
	public void start() throws Exception {
		for (Class clazz : this.getClasses()) {
			this.context.registerMapper(clazz);
			logger.info("register mapper:[" + clazz.getName() + "]");
		}
	}

	@Override
	public void stop() throws Exception {
		
	}

}
