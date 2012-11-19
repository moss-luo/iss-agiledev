package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.hibernate.validator.constraints.Length;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

import com.isoftstone.agiledev.core.init.AbstractInitializeSupport;
import com.isoftstone.agiledev.core.init.Init;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;
import com.isoftstone.agiledev.core.validate.ValidateData;
import com.isoftstone.agiledev.core.validate.ValidateParser;



public class EasyUIValidateInitial extends AbstractInitializeSupport implements InitializeAdaptor,BundleContextAware,BundleListener{

	
	private BundleContext bundleContext = null;
	
	private Map<Long,Vector<ValidateData>> validateTypes = null;
	
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
				validateField = this.getValidateField(field.getName(),field.getAnnotations());
				this.pushValidateField(validateField);
			}
			
			Method[] methods = clazz.getDeclaredMethods();
			for (Method method : methods) {
				if(method.getName().startsWith("set")){
					String fieldName = method.getName().substring(3);
					fieldName = fieldName.substring(0,1).toLowerCase()+fieldName.substring(1);
					validateField = this.getValidateField(fieldName,method.getAnnotations());
					this.pushValidateField(validateField);
				}
			}
		}
	}
	
	
	private EasyUIInitField getValidateField(String fieldName,Annotation[] annotations){
		try {

			EasyUIInitField validateField = (EasyUIInitField) this.getField(fieldName);
			if(validateField == null){
				validateField = new EasyUIInitField();
				validateField.setName(fieldName);
			}
			
			for (Annotation annotation : annotations) {
					validateField = (EasyUIInitField) this.fillValidateField(validateField,annotation);
			}
			if(annotations.length>0){
				populateValidate(validateField);	
			}
			return validateField;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void populateValidate(EasyUIInitField validateField) {
		String validate=validateField.getValidate();
		if(validate!=null && validate.indexOf(";")>-1){
			String[] validates=validate.split(";");
			String compositValidate="composit[";
			String fn="";
			for (int i = 0; i < validates.length; i++) {
				
				fn="{'fn':'"+ validates[i].substring(0, validates[i].indexOf(":"))+"',";
				if(validates[i].substring(validates[i].indexOf(":")+1)!=null){
					fn+="'msg':'"+validates[i].substring(validates[i].indexOf(":")+1)+"'";
				}
				if(i==(validates.length-1)){
					compositValidate+=(fn+"}");
				}else{
					compositValidate+=(fn+"},");
				}				
			}
			compositValidate+="]";
			validateField.setValidate(compositValidate);
			validateField.setDefMessage(null);
		}else if(validate!=null && validate.indexOf(";")==-1 && !validate.contains("{")){
			int index=validate.indexOf(":");
			if(index>-1){
				validateField.setValidate(validate.substring(0, index));
			}
		}
	}
	
	public InitField fillValidateField(EasyUIInitField validateField,Annotation annotation){
		
		try {

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
			if(Init.class.getName().equals(clazz.getName())){
				return validateField;
			}
			String compositValidate=validateField.getValidate();
			if(compositValidate!=null){
				compositValidate+=";";
			}else{
				compositValidate="";
			}
			for (Vector<ValidateData> v : validateTypes.values()) {
				for (ValidateData validateData : v) {				
					if(validateData.getAnnotationClass().equals(clazz.getName())){						
						ValidateParser parser = validateData.getParser();
						if(parser!=null){							
							parser.buildValidate(validateField,annotation);
							compositValidate+=validateField.getValidate();
							compositValidate+=":"+validateField.getDefMessage();
							validateField.setValidate(compositValidate);
							return validateField;
							
						}else if(validateData.getExpression().equalsIgnoreCase("required")){
							validateField.setRequired(true);
							return validateField;
						}else{
							compositValidate+=validateData.getExpression();
							
							compositValidate+=":";
							validateField.setValidate(compositValidate);
							return validateField; 
						}
						

					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		return validateField;
	}

	public void pushValidateField(EasyUIInitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			EasyUIInitField validateField = (EasyUIInitField) field;
			validateField.appendValidate(f.getValidate());
			validateField.setRequired(f.isRequired());
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
			validateTypes = new HashMap<Long,Vector<ValidateData>>();
			
			synchronized (this) {
				Vector<ValidateData> types = new Vector<ValidateData>();
				for (Entry<Object, Object> entry : properties.entrySet()) {
					ValidateData validateData = new ValidateData();
					validateData.setAnnotationClass(entry.getKey().toString());
					String[] value = entry.getValue().toString().split(",");
					for(String s:value){
						if(!s.contains("="))continue;
						if(s.split("=")[0].equalsIgnoreCase("expression")){
							validateData.setExpression(s.split("=")[1]);
						}
						if(s.split("=")[0].equalsIgnoreCase("parser")){
							ValidateParser parser = (ValidateParser) this.getClass().getClassLoader().loadClass(s.split("=")[1]).newInstance();
							validateData.setParser(parser);
						}
					}
					types.add(validateData);
				}
				validateTypes.put(bundle.getBundleId(), types);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
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
