package com.isoftstone.agiledev.osgi.core.action;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.commons.util.OSGIUtil;
import com.isoftstone.agiledev.osgi.core.Context;
import com.isoftstone.agiledev.osgi.db.dao.SessionCreater;

public class DefaultBundleContext implements Context {

	private ClassLoader classLoader = null;
	private BundleContext bundleContext = null;
	private Logger logger = LoggerFactory.getLogger(DefaultBundleContext.class);

	@Override
	public void setClassLoader(ClassLoader classLoader) {
		this.classLoader = classLoader;
	}

	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	
	/**
	 * 获取当前bundle中的所有类
	 */
	@Override
	public Class<?>[] getContextClasses() {

		try {
			String[] classNames = this.getBundleClassNames(this.bundleContext);
			List<Class<?>> classes = new ArrayList<Class<?>>();
			java.lang.ClassLoader loader = this.classLoader;
			for (String s : classNames) {
				Class<?> clazz = loader.loadClass(s);
				classes.add(clazz);
			}
		return classes.toArray(new Class[] {});
		} catch (Exception e) {
			logger.error("error in DefaultServiceRegister!",e);
		}
		return null;
	}


	private String[] getBundleClassNames(BundleContext context)
			throws IOException {

		String jarPath = getBundlePath(context);
		String[] classNames = OSGIUtil.readJarFileClass(jarPath);
		return classNames;
	}
	/**
	 * 获取bundle-jar的物理位置
	 * @param context
	 * @return
	 */
	private String getBundlePath(BundleContext context) {

		String bundleName = context.getBundle().getSymbolicName();
		String bundlesInfo = System.getProperty("osgi.bundles");

		int bundleNameStart = bundlesInfo.indexOf(bundleName);
		int bundleNameEnd = bundleNameStart + bundleName.length();
		String prependedBundlePath = bundlesInfo.substring(0, bundleNameEnd);
		String prefix = "reference:file:";
		int prefixPos = prependedBundlePath.lastIndexOf(prefix);
		String bundlePath = prependedBundlePath;
		if (prefixPos >= 0) {
			bundlePath = prependedBundlePath.substring(
					prefixPos + prefix.length(), prependedBundlePath.length());
		}
		String jarPath = System.getProperty("user.dir") + File.separator
				+ bundlePath + "_1.0.0.SNAPSHOT.jar";
		return jarPath;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void registerService(String name, Object o, Dictionary props) {
		this.bundleContext.registerService(name, o, props);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void registerService(Class<?> clazz, Object o, Dictionary props) {
		this.registerService(clazz.getName(), o, props);
	}


	@Override
	public void registerService(String name, Object o) {
		this.registerService(name, o, null);
	}

	@Override
	public void registerService(Class<?> clazz, Object o) {
		this.registerService(clazz, o, null);
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void registerDomain(String alias, Class<?> clazz) {

		try {
			SessionCreater creater = null;
			ServiceReference sf = this.bundleContext.getServiceReference(SessionCreater.class.getName());
			if (sf != null) {
				creater = (SessionCreater) this.bundleContext.getService(sf);
			} else {
				return;
			}
			creater.registerDomain(alias, clazz);
		} catch (Exception e) {
			logger.error("error in DefaultBundleContext.registerDomain!",e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void registerMapper(Class<?> clazz) {
		try {
			SessionCreater creater = null;
			ServiceReference sf = this.bundleContext.getServiceReference(SessionCreater.class.getName());
			if (sf != null) {
				creater = (SessionCreater) this.bundleContext.getService(sf);
			} else {
				return;
			}
			creater.registerMapper(clazz);
		} catch (Exception e) {
			logger.error("error in DefaultBundleContext.registerMapper!",e);
		}
		
	}


	@SuppressWarnings("rawtypes")
	@Override
	public void unregisterService(String name, String filter) {
		try {
			if(filter!=null){
				ServiceReference sf = this.bundleContext.getServiceReference(name);
				this.bundleContext.ungetService(sf);
			}else{
				ServiceReference[] sfs = this.bundleContext.getServiceReferences(name, filter);
				if(sfs!=null && sfs.length>0){
					for (ServiceReference sf : sfs) {
						this.bundleContext.ungetService(sf);
					}
				}
			}
		} catch (Exception e) {
			logger.error("error in DefaultBundleContext.unregisterService!",e);
		}
				
	}

	@Override
	public void unregisterService(String name) {
		this.unregisterService(name,null);
	}
	@Override
	public void unregisterService(Class<?> clazz) {
		this.unregisterService(clazz.getName(),null);
	}

	@Override
	public void unregisterService(Class<?> clazz, String filter) {
		this.unregisterService(clazz.getName(),filter);		
	}



}
