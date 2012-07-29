package com.isoftstone.agiledev.osgi.core.register;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public abstract class DefaultAnnotationServiceRegister extends DefaultServiceRegister implements BeanRegister{

	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] getClasses() {
		Class<?>[] allBundleClasses = this.context.getContextClasses();
		List<Class<?>> annotationClasses = new ArrayList<Class<?>>();
		for (Class<?> c : allBundleClasses) {
			Class<? extends Annotation> annotationType = (Class<? extends Annotation>) this.getRegisterType();
			if(c.isAnnotationPresent(annotationType)){
				annotationClasses.add(c);
			}
		}
		return annotationClasses.toArray(new Class<?>[]{});
	}
	
}
