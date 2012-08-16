package com.isoftstone.agiledev.osgi.core.register;

import com.isoftstone.agiledev.osgi.core.inject.Injecter;

public abstract class DefaultServiceRegister extends AbstractRegister implements BeanRegister{

	protected Injecter injecter = null;
	
	@Override
	public void setInjecter(Injecter injecter) {
		this.injecter = injecter;
	}
	
	@Override
	public abstract void start() throws Exception;

	@Override
	public abstract void stop() throws Exception;

}
