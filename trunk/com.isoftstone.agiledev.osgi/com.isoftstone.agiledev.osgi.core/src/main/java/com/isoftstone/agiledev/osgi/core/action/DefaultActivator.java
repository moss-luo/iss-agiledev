package com.isoftstone.agiledev.osgi.core.action;

/**
 * 管理端默认的Activator抽象类，用于扫描当前bundle中的Action，Domain，Mapper，Menu
 * @author david
 */
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

import com.isoftstone.agiledev.osgi.core.Activator;
import com.isoftstone.agiledev.osgi.core.Register;
import com.isoftstone.agiledev.osgi.core.inject.Injecter;
import com.isoftstone.agiledev.osgi.core.inject.OSGIClassLoader;
import com.isoftstone.agiledev.osgi.core.register.ActionRegister;
import com.isoftstone.agiledev.osgi.core.register.BeanRegister;
import com.isoftstone.agiledev.osgi.core.register.DomainRegister;
import com.isoftstone.agiledev.osgi.core.register.MapperRegister;
import com.isoftstone.agiledev.osgi.core.register.ServiceRegister;
import com.isoftstone.agiledev.osgi.core.web.ActionContext;
import com.isoftstone.agiledev.osgi.db.dao.SessionCreater;

public abstract class DefaultActivator implements Activator,
		ServiceListener {
	private List<Register> registers = null;
	
	protected BundleContext context = null;
	
	
	
	@Override
	public void init() {
		registers = new ArrayList<Register>();
		
		DefaultBundleContext context = new DefaultBundleContext();
		context.setBundleContext(this.context);
		context.setClassLoader(this.getClass().getClassLoader());
		
		Injecter injecter = new Injecter();
		OSGIClassLoader classLoader = new OSGIClassLoader();
		classLoader.setBundleContext(this.context);
		injecter.setClassLoader(classLoader);

		
		BeanRegister serviceRegister = new ServiceRegister();
		serviceRegister.setContext(context);
		serviceRegister.setInjecter(injecter);
		registers.add(serviceRegister);
		
		BeanRegister actionRegister = new ActionRegister();
		actionRegister.setContext(context);
		actionRegister.setInjecter(injecter);
		registers.add(actionRegister);
		
		BeanRegister domainRegister = new DomainRegister();
		domainRegister.setContext(context);
		registers.add(domainRegister);
		
		BeanRegister mapperRegister = new MapperRegister();
		mapperRegister.setContext(context);
		registers.add(mapperRegister);
		
	}
	
	public void start(BundleContext context) throws Exception {
		ActionContext.setBundleContext(context);
		this.context = context;
		this.init();
		this.startBundle(context);
		this.register();
		
		context.addServiceListener(this,
				"(objectClass=" + SessionCreater.class.getName() + ")");
	}

	public void stop(BundleContext context) throws Exception {
		this.stopBundle(context);
		this.context.removeServiceListener(this);
	}

	protected abstract void startBundle(BundleContext arg0) throws Exception;

	protected abstract void stopBundle(BundleContext arg0) throws Exception;
	
	@Override
	public void register() throws Exception {

		for (Register r : this.registers) {
			r.start();
		}
		
	}
	@Override
	public void unregister() throws Exception {

		for (Register r : this.registers) {
			r.stop();
		}
	}
	
	//TODO 实现DAOProxy，如果dbbundle停掉，需要proxy接管过去
	@Override
	public void serviceChanged(ServiceEvent event) {
		switch (event.getType()) {
		case ServiceEvent.REGISTERED:
//				registerDomain();
			break;

		case ServiceEvent.UNREGISTERING:
			break;
		}

	}
}
