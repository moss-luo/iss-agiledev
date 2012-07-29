package com.isoftstone.agiledev.osgi.core.action;

/**
 * 管理端默认的Activator抽象类，用于扫描当前bundle中的Action，Domain，Mapper，Menu
 * @author david
 */
import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
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
//
//	public void serviceChanged(ServiceEvent event) {
//		switch (event.getType()) {
//		case ServiceEvent.REGISTERED:
//			try {
//				registerDomain();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			break;
//
//		case ServiceEvent.UNREGISTERING:
//			break;
//		}
//
//	}
//
//	/**
//	 * 注册Bundle action
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	protected void registerAction() {
//		try {
//			for (Class clazz : this.actionClasses) {
//				Action annotation = (Action) clazz.getAnnotation(Action.class);
//				Object o = clazz.newInstance();
//				if (o instanceof com.isoftstone.agiledev.osgi.core.web.Action) {
//					Dictionary props = new Properties();
//					props.put("path", annotation.packageName() + "/"
//							+ annotation.path());
//					this.register(
//							com.isoftstone.agiledev.osgi.core.web.Action.class,
//							o, props);
//					logger.info("register Action:" + annotation.packageName()
//							+ "/" + annotation.path());
//				} else {
//					throw new Exception(
//							"class "
//									+ clazz
//									+ " is not an instance of the service class com.isoftstone.mgt.console.core.web.Action");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 注册bundle domain
//	 * 
//	 * @param context
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	protected void registerDomain() {
//		try {
//			SessionCreater creater = null;
//			ServiceReference sf = context
//					.getServiceReference(SessionCreater.class.getName());
//			if (sf != null) {
//				creater = (SessionCreater) context.getService(sf);
//			} else {
//				return;
//			}
//			for (Class clazz : this.domainClasses) {
//				// 注册Domain
//				EntityAlias annotation = (EntityAlias) clazz.getAnnotation(EntityAlias.class);
//				if (creater != null) {
//					if (annotation.value() != null)
//						creater.registerDomain(annotation.value(), clazz);
//					else
//						creater.registerDomain(clazz.getName(), clazz);
//					logger.info("register domain:[" + clazz.getName() + "]");
//				}
//			}
//			for (Class clazz : this.mapperClasses) {
//				// 注册Mapper
//				if (creater != null) {
//					creater.registerMapper(clazz);
//					logger.info("register mapper:[" + clazz.getName() + "]");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * 注册bundle service
//	 * 
//	 * @param context
//	 * @throws IOException
//	 * @throws ClassNotFoundException
//	 * @throws InstantiationException
//	 * @throws IllegalAccessException
//	 */
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	protected void registerService() throws IOException,
//			ClassNotFoundException, InstantiationException,
//			IllegalAccessException, Exception {
//		for (Class clazz : this.serviceClasses) {
//			Service annotation = (Service) clazz.getAnnotation(Service.class);
//			Class<?> superClazz = annotation.type();
//			if (superClazz != null && superClazz != Object.class) {
//				Class<?>[] parents = clazz.getInterfaces();
//				for (Class<?> c : parents) {
//					if (c.getName().equals(superClazz.getName())) {
//						superClazz = c;
//					}
//				}
//			} else if (clazz.getInterfaces().length != 0) {
//				Class<?>[] parents = clazz.getInterfaces();
//				if (parents != null && parents.length >= 1) {
//					superClazz = parents[0];
//				}
//			} else {
//				logger.error("service [" + clazz.getName()
//						+ "] as least implement an interface");
//				throw new Exception("service [" + clazz.getName()
//						+ "] as least implement an interface");
//			}
//
//			String name = annotation.name();
//			Dictionary props = new Properties();
//			if (name != null && !"".equals(name)) {
//				props.put("serviceName", name);
//			} else {
//				props.put("serviceName", clazz.getName());
//			}
//			this.register(superClazz, clazz.newInstance(), props);
//			logger.info("register service:[" + clazz.getName() + "]");
//		}
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	private Object injectService(Object o) {
//		try {
//			Class<?> clazz = o.getClass();
//			for (Field f : clazz.getDeclaredFields()) {
//				boolean oldAccess = f.isAccessible();
//				f.setAccessible(true);
//				if (f.isAnnotationPresent(Resource.class)) {
//					Resource resource = f.getAnnotation(Resource.class);
//					String serviceName = resource.name();
//					ServiceReference sf = null;
//					if (serviceName != null && !"".equals(serviceName)) {
//						ServiceReference[] sfs = this.context
//								.getServiceReferences(f.getType().getName(),
//										"(serviceName=" + serviceName + ")");
//						if (sfs != null && sfs.length > 0)
//							sf = sfs[0];
//					} else {
//						sf = this.context.getServiceReference(f.getType().getName());
//					}
//					if (sf != null) {
//						Object serviceInstance = this.context.getService(sf);
////						BeanUtils.setProperty(o, f.getName(), serviceInstance);
//						serviceInstance = this.injectService(serviceInstance);
//						f.set(o, serviceInstance);
//					}
//				}
//				f.setAccessible(oldAccess);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return o;
//	}
//
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	private void register(Class<?> superClass, Object serviceInstance,
//			Dictionary props) {
//
//		serviceInstance = this.injectService(serviceInstance);
//		this.context.registerService(superClass.getName(), serviceInstance,props);
//	}
//	
//	
//
//	private List<Class<?>> serviceClasses = null;
//	private List<Class<?>> actionClasses = null;
//	private List<Class<?>> domainClasses = null;
//	private List<Class<?>> mapperClasses = null;
//	/**
//	 * 初始化bundle下面的所有类
//	 * @throws IOException
//	 * @throws ClassNotFoundException
//	 */
//	private void initClasses() throws IOException, ClassNotFoundException{
//
//		this.serviceClasses = new ArrayList<Class<?>>();
//		this.actionClasses = new ArrayList<Class<?>>();
//		this.domainClasses = new ArrayList<Class<?>>();
//		this.mapperClasses = new ArrayList<Class<?>>();
//
//		Class<?>[] classes = this.getBundleClasses(context);
//
//		for (Class<?> clazz : classes) {
//			if (clazz.isAnnotation())
//				continue;
//			if (clazz.isAnnotationPresent(Service.class))
//				serviceClasses.add(clazz);
//			if (clazz.isAnnotationPresent(Action.class))
//				actionClasses.add(clazz);
//			if (clazz.isAnnotationPresent(EntityAlias.class))
//				domainClasses.add(clazz);
//			if (clazz.isAnnotationPresent(EntityMapper.class))
//				mapperClasses.add(clazz);
//		}
//	}
//	
//	
//	/**
//	 * 获取bundle中的所有类
//	 * 
//	 * @param context
//	 * @return
//	 * @throws IOException
//	 */
//	private String[] getBundleClassNames(BundleContext context)
//			throws IOException {
//
//		String jarPath = getBundlePath(context);
//		String[] classNames = OSGIUtil.readJarFileClass(jarPath);
//		return classNames;
//	}
//
//	private Class<?>[] getBundleClasses(BundleContext context)
//			throws IOException, ClassNotFoundException {
//		String[] classNames = this.getBundleClassNames(context);
//		List<Class<?>> classes = new ArrayList<Class<?>>();
//		ClassLoader loader = this.getClass().getClassLoader();
//		for (String s : classNames) {
//			Class<?> clazz = loader.loadClass(s);
//			classes.add(clazz);
//		}
//		return classes.toArray(new Class[] {});
//	}
//
//	/**
//	 * 获取bundle-jar的物理位置
//	 * 
//	 * @param context
//	 * @return
//	 */
//	private String getBundlePath(BundleContext context) {
//
//		String bundleName = context.getBundle().getSymbolicName();
//		String bundlesInfo = System.getProperty("osgi.bundles");
//
//		int bundleNameStart = bundlesInfo.indexOf(bundleName);
//		int bundleNameEnd = bundleNameStart + bundleName.length();
//		String prependedBundlePath = bundlesInfo.substring(0, bundleNameEnd);
//		String prefix = "reference:file:";
//		int prefixPos = prependedBundlePath.lastIndexOf(prefix);
//		String bundlePath = prependedBundlePath;
//		if (prefixPos >= 0) {
//			bundlePath = prependedBundlePath.substring(
//					prefixPos + prefix.length(), prependedBundlePath.length());
//		}
//		String jarPath = System.getProperty("user.dir") + File.separator
//				+ bundlePath + "_1.0.0.SNAPSHOT.jar";
//		return jarPath;
//	}
//	@Override
//	public BundleContext getBundleContext() {
//		return this.context;
//	}
}
