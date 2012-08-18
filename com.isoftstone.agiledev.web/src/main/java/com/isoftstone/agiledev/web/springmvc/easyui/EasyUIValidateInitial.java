package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

import com.isoftstone.agiledev.core.init.AbstractInitializeSupport;
import com.isoftstone.agiledev.core.init.InitChain;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;



public class EasyUIValidateInitial extends AbstractInitializeSupport implements InitializeAdaptor,BundleContextAware{

	
	private static Properties validateTypes = null;
	private BundleContext bundleContext = null;
	
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
		Enumeration<URL> resources = bundle.findEntries("/agiledev", "validate.properties", false);
		if(resources==null)return;
		if(!resources.hasMoreElements())return;
		URL resource = resources.nextElement();
		validateTypes = new Properties();
		try {
			validateTypes.load(resource.openStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<InitField> doInit(HttpServletRequest request,InitializeModel model,InitChain initChain) {

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
		return this.initFields;
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
				String validateType = validateTypes.getProperty(clazz.getName());
				if(validateType!=null)
					sb.append(validateType).append(";");
			}
		} catch (Exception e) {
			// TODO: handle exception
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
}
