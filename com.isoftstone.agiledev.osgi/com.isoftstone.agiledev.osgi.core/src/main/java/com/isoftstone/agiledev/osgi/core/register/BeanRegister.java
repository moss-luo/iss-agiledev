package com.isoftstone.agiledev.osgi.core.register;

import com.isoftstone.agiledev.osgi.core.Register;
import com.isoftstone.agiledev.osgi.core.inject.Injecter;

public interface BeanRegister extends Register {
	void setInjecter(Injecter injecter);
	
	Class<?>[] getClasses();
}
