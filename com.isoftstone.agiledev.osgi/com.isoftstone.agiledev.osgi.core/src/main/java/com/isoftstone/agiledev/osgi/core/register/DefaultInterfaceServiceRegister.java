package com.isoftstone.agiledev.osgi.core.register;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DefaultInterfaceServiceRegister extends DefaultServiceRegister implements BeanRegister{

	private Logger logger = LoggerFactory.getLogger(DefaultInterfaceServiceRegister.class);
	@Override
	public Class<?>[] getClasses() {
		Class<?>[] allBundleClasses = this.context.getContextClasses();
		List<Class<?>> implementClasses = new ArrayList<Class<?>>();
		try {
				
			for (Class<?> c : allBundleClasses) {
				for (Class<?> interfaceClasse : c.getInterfaces()) {
					if(interfaceClasse.getName().equals(this.getRegisterType().getName())){
						implementClasses.add(c);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error in DefaultInterfaceServiceRegister!",e);
		}
		return implementClasses.toArray(new Class<?>[]{});
	}
	@Override
	public void start() throws Exception {
		for (Class<?> c : this.getClasses()) {
			this.context.registerService(this.getRegisterType(), c.newInstance(), null);
		}
	}
	@Override
	public void stop() throws Exception {
		for (Class<?> c : this.getClasses()) {
			this.context.unregisterService(c);
		}
	}
}

