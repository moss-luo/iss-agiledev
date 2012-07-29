package com.isoftstone.agiledev.osgi.core.register;

import java.util.ArrayList;
import java.util.List;

public abstract class DefaultAnnotationServiceRegister extends DefaultServiceRegister implements AnnotationServiceRegister{

	@Override
	public Class<?>[] getAnnotationClasses() {
		Class<?>[] allBundleClasses = this.context.getContextClasses();
		List<Class<?>> annotationClasses = new ArrayList<Class<?>>();
		for (Class<?> c : allBundleClasses) {
			if(c.isAnnotationPresent(this.getAnnotationType())){
				annotationClasses.add(c);
			}
		}
		return annotationClasses.toArray(new Class<?>[]{});
	}
	
}
