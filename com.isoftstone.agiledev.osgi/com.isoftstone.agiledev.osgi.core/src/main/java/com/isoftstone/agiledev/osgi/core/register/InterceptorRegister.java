package com.isoftstone.agiledev.osgi.core.register;

import com.isoftstone.agiledev.osgi.core.web.Interceptor;

public class InterceptorRegister  extends DefaultInterfaceServiceRegister{

	@Override
	public Class<?> getRegisterType() {
		return Interceptor.class;
	}


}
