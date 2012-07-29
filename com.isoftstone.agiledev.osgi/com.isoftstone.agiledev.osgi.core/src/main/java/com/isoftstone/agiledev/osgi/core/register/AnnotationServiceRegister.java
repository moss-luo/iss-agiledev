package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;


public interface AnnotationServiceRegister extends BeanRegister{

	Class<? extends Annotation> getAnnotationType();
	
	Class<?>[] getAnnotationClasses();
}
