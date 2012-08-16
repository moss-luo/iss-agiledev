package com.isoftstone.agiledev.osgi.core.register;

import com.isoftstone.agiledev.osgi.core.Context;
import com.isoftstone.agiledev.osgi.core.Register;

public abstract class AbstractRegister implements Register {

	protected Context context;

	@Override
	public abstract void start() throws Exception;

	@Override
	public abstract void stop() throws Exception;

	@Override
	public void setContext(Context context) {
		this.context = context;
	}

}
