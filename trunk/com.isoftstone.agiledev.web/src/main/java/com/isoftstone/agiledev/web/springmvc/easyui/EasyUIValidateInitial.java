package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.isoftstone.agiledev.core.init.AbstractInitializeSupport;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;



public class EasyUIValidateInitial extends AbstractInitializeSupport implements InitializeAdaptor,BundleContextAware,BundleListener{

	
	private BundleContext bundleContext = null;
	
	private Map<Long,Map<String,String>> validateTypes = null;
	
	public EasyUIValidateInitial() {
	}
	
	public EasyUIValidateInitial(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	private void init(){
		Bundle bundle = null;
		for(Bundle b:this.bundleContext.getBundles()){
			if(b.toString().indexOf("agiledev.web")!=-1){
				bundle = b;
				break;
			}
		}
		registerValidateIfExisting(bundle);
	}
	@Override
	public void doInit(HttpServletRequest request,InitializeModel model) {

		if(validateTypes==null){
			init();
		}
		
		EasyUIInitField validateField = null;
		for (Class<?> clazz: model.getInitialiedType()) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String validateType = this.getValidateType(field);
				if(validateType!=null && !"".equals(validateType)){
					validateField = new EasyUIInitField();
					String name = field.getName();
					validateField.setName(name);
					validateField.appendValidate(validateType);
					this.pushValidateField(validateField);
				}
			}
		}
//		return this.initFields;
	}
	
	private String getValidateType(Field field){
		StringBuilder sb = new StringBuilder();
		try {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation annotation : annotations) {
	
				Class<?> clazz = null;
				if(annotation instanceof Proxy){
					InvocationHandler handle = Proxy.getInvocationHandler(annotation); 
					Field f = handle.getClass().getDeclaredField("type");
					f.setAccessible(true);
					clazz = (Class<?>) f.get(handle);
					f.setAccessible(false);
				}else{
					clazz = annotation.getClass();
				}
				String validateType = null;
				for (Map<String,String> m : validateTypes.values()) {
					Iterator<String> it = m.keySet().iterator();
					while(it.hasNext()){
						String t = it.next();
						if(t.equals(clazz.getName())){
							validateType = m.get(t);
							break;
						}
					}
				}
				if(validateType!=null)
					sb.append(validateType).append(";");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public void pushValidateField(EasyUIInitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			EasyUIInitField validateField = (EasyUIInitField) field;
			validateField.appendValidate(f.getValidate());
		}else{
			this.addInitField(f);
		}
	}
	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	@Override
	public void bundleChanged(BundleEvent event) {

		if (event.getType() == BundleEvent.STARTED) {
			registerValidateIfExisting(event.getBundle());
		} else if (event.getType() == BundleEvent.STOPPED) {
			unregisterValidateIfExisting(event.getBundle());
		}
	}
	/**
	 * 扩展其他自定义校验注解
	 * @param bundle
	 */
	private void registerValidateIfExisting(Bundle bundle){

		Enumeration<URL> resources = bundle.findEntries("/agiledev", "validate.properties", false);
		if(resources==null)return;
		if(!resources.hasMoreElements())return;
		URL resource = resources.nextElement();
		Properties properties = new Properties();
		try {
			properties.load(resource.openStream());
			validateTypes = new HashMap<Long,Map<String,String>>();
			synchronized (this) {
				Map<String,String> temp = new HashMap<String, String>();
				for (Entry<Object, Object> entry : properties.entrySet()) {
					temp.put((String)entry.getKey(), (String)entry.getValue());
				}
				validateTypes.put(bundle.getBundleId(), temp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private synchronized void unregisterValidateIfExisting(Bundle bundle){
		if(validateTypes.containsKey(bundle.getBundleId())){
			synchronized(this){
				validateTypes.remove(bundle.getBundleId());
			}
		}
	}
}
