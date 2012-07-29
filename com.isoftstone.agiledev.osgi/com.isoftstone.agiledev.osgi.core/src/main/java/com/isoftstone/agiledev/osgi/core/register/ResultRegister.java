package com.isoftstone.agiledev.osgi.core.register;

import com.isoftstone.agiledev.osgi.core.web.Result;

public class ResultRegister extends DefaultInterfaceServiceRegister{

	@Override
	public Class<?> getRegisterType() {
		return Result.class;
	}


}
