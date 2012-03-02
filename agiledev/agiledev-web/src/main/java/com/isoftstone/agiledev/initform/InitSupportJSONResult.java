package com.isoftstone.agiledev.initform;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts2.json.JSONResult;

import com.isoftstone.agiledev.validater.ValidateInterceptor;
import com.isoftstone.agiledev.validater.Validation;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator;

/**
 * 返回对象初始化数据及验证信息
 * @author xwpu
 *
 */
@SuppressWarnings("serial")
public class InitSupportJSONResult extends JSONResult {	
	private Object action;
	private List<InitField> initFields = new ArrayList<InitField>();

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		action = invocation.getAction();
        super.execute(invocation);
	}
	@Override
	protected void writeToResponse(HttpServletResponse response, String json, boolean gzip) throws IOException {

		Class<?> clazz = action.getClass();
		//处理初始数据
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Initialization.class)){
					Class<?> targetInitializaObjectClazz = field.getType();
					Field[] targetValidateObjectFields = targetInitializaObjectClazz.getDeclaredFields();
					for (Field initializaField : targetValidateObjectFields) {
						InitField initField = null;
						//获取注解配置的初始值
						if(initializaField.isAnnotationPresent(Initializa.class)){
							Initializa init = initializaField.getAnnotation(Initializa.class);
							String value = init.value();
							initField = new InitField();
							if(action instanceof ModelDriven){
								initField.setName(initializaField.getName());
							}else{
								initField.setName(field.getName()+"."+initializaField.getName());
								
							}
							initField.setInitValue(value);
							this.pushFieldInit(initField);
						}
					}
					//获取接口返回的初始值
					try {
						Object targetInitializaObject = targetInitializaObjectClazz.newInstance();
						if(targetInitializaObject instanceof InitProvider){
							InitProvider provider = (InitProvider) targetInitializaObject;
							Map<String,Object> initValues = provider.initModel();
							if(initValues!=null && initValues.size()>0){
								for (String s : initValues.keySet()) {
									InitField initField = new InitField();
									initField.setName(s);
									initField.setInitValue(initValues.get(s));
									this.pushFieldInit(initField);
								}
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			}
			clazz = clazz.getSuperclass();
		}
		//处理验证
		clazz = action.getClass();
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Validation.class)){
					Class<?> targetValidateObjectClazz = field.getType();
					Field[] targetValidateObjectFields = targetValidateObjectClazz.getDeclaredFields();
					for (Field validateField : targetValidateObjectFields) {

						Annotation[] annotations = validateField.getAnnotations();
						if(annotations.length>0){
							if(action instanceof ModelDriven){
								getInitField(annotations, validateField.getName());
							}else{
								getInitField(annotations, field.getName()+"."+validateField.getName());
							}
						}
					}
					
					
					Method[] targetValidateObjectMethods = targetValidateObjectClazz.getDeclaredMethods();
					for (Method validateMethod : targetValidateObjectMethods) {
						Annotation[] annotations = validateMethod.getAnnotations();
						if(annotations.length>0){
							if(action instanceof ModelDriven){
								getInitField(annotations,AnnotationUtils.resolvePropertyName(validateMethod));
							}else{
								getInitField(annotations,field.getName()+"."+AnnotationUtils.resolvePropertyName(validateMethod));
							}
						}
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		JSONArray fields = JSONArray.fromObject(this.initFields);
		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(String.format("{\"field\":%s}",fields.toString()));
		out.flush();
		out.close();
		
		//super.writeToResponse(response, json, gzip);
	}
	
	private InitField getInitField(Annotation[] annotations,String fieldName){
		InitField initField = null;
		try {
			
			for (Annotation annotation : annotations) {
				
				Object o = null;
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
				o = ValidateInterceptor.getValidator(clazz);
				
				if(o!=null){
					if(o instanceof FieldValidator){
						FieldValidator validator = (FieldValidator) o;
						initField = new InitField();
						if(validator instanceof com.opensymphony.xwork2.validator.validators.RequiredStringValidator 
								|| validator instanceof com.opensymphony.xwork2.validator.validators.RequiredFieldValidator){
							initField.setRequired(true);
						}
						if(validator instanceof StringLengthFieldValidator){
							com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator lengthAnnotation = 
								(com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator) annotation;
							if(lengthAnnotation.minLength()!=null && !"".equals(lengthAnnotation.minLength())){
								initField.setRequired(true);
							}
							validator.setValidatorType("length["+lengthAnnotation.minLength()+","+lengthAnnotation.maxLength()+"]");
						}		
						initField.appendValidate(validator.getValidatorType());
					}
				}
			}
	
			if(initField!=null){
				initField.setName(fieldName);
				this.pushFieldValidate(initField);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return initField;
	}
	
	
	
	/**
	 * 添加初始字段
	 * @param f
	 */
	private void pushFieldInit(InitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			field.setInitValue(f.getInitValue());
		}else{
			this.initFields.add(f);
		}
	}
	/**
	 * 添加字段验证类型
	 * @param f
	 */
	private void pushFieldValidate(InitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			field.appendValidate(f.getValidate());
			field.setRequired(f.isRequired());
		}else{
			this.initFields.add(f);
		}
	}
	private InitField getField(String fieldName){
		for (InitField field : this.initFields) {
			if(field.getName().equals(fieldName))
				return field;
		}
		return null;
	}
}
