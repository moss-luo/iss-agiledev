package com.isoftstone.agiledev.initform;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.isoftstone.agiledev.validater.ValidateInterceptor;
import com.isoftstone.agiledev.validater.Validation;
import com.isoftstone.agiledev.validater.Validations;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.AnnotationUtils;
import com.opensymphony.xwork2.validator.FieldValidator;
import com.opensymphony.xwork2.validator.validators.StringLengthFieldValidator;
/**
 * 构造验证数据json
 * @author sinner
 *
 */
public class ValidateResult extends AbstractResult {

	private Object action = null;
	@Override
	public void doResult(HttpServletResponse response, ActionInvocation invocation) {

		this.action = invocation.getAction();
		//处理验证
		try {
			Class<?> clazz = action.getClass();
			while (clazz != Object.class) {
				Field[] fields = clazz.getDeclaredFields();
				for (Field field : fields) {
					if(field.isAnnotationPresent(Validation.class)){
						this.singularHandle(field);
					}else if(field.isAnnotationPresent(Validations.class)){
						Validations vs = field.getAnnotation(Validations.class);
						pluralHandle(field,vs);
					}
				}
				clazz = clazz.getSuperclass();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 如果Validation作为Validations的一项。
	 * @param field
	 * @param v
	 */
	private void pluralHandle(Field field,Validations vs)throws Exception{
		for (Validation v : vs.value()) {
			String fieldName = v.fieldName();
			Class<? extends Annotation> annotationClass = v.validType();
//			Annotation validateAnnotation = null;
			Object validator = ValidateInterceptor.getValidator(annotationClass);
			
//			Method[] ms = annotationClass.getDeclaredMethods();
			String[] params = v.params();
			if(params.length%2!=0){
				throw new Exception("The parameter params of @Validation("+annotationClass.getName()+") must be keep even number");
			}
//			for(int i=0;i<params.length;i++){
//				for (Method m : ms) {
//					if(m.getName().equals(params[i])){
//						m.invoke(validateAnnotation, new Object[]{params[i+1]});
//						++i;
//						break;
//					}
//				}
//			}
			Map<String,String> annotationParameter = new HashMap<String,String>();
			for(int i=0;i<params.length;i++){
				annotationParameter.put(params[i], params[i+1]);
				++i;
			}
			this.pushValidation(validator,fieldName,annotationParameter);
		}
	}
	/**
	 * 如果Validation直接注解在某个字段上的处理,需要反射该字段对应类，读取具体的校验注解
	 * @param field
	 */
	private void singularHandle(Field field){
		Class<?> targetValidateObjectClazz = field.getType();
		Field[] targetValidateObjectFields = targetValidateObjectClazz.getDeclaredFields();
		for (Field validateField : targetValidateObjectFields) {
			Annotation[] annotations = validateField.getAnnotations();
			if(annotations.length>0){
				String fieldName = field.getName()+"."+validateField.getName();
				if(action instanceof ModelDriven){
					fieldName = validateField.getName();
				}
				for (Annotation annotation : annotations) {
					pushValidation(fieldName,annotation);
				}
			}
		}
		Method[] targetValidateObjectMethods = targetValidateObjectClazz.getDeclaredMethods();
		for (Method validateMethod : targetValidateObjectMethods) {
			Annotation[] annotations = validateMethod.getAnnotations();
			if(annotations.length>0){
				String fieldName = field.getName()+"."+AnnotationUtils.resolvePropertyName(validateMethod);
				if(action instanceof ModelDriven){
					fieldName = AnnotationUtils.resolvePropertyName(validateMethod);
				}
				for (Annotation annotation : annotations) {
					pushValidation(fieldName,annotation);
				}
				
			}
		}
	}
	/**
	 * 追加校验信息
	 * @param validator 校验器
	 * @param fieldName 字段名称
	 * @param annotation 校验注解
	 */
	private void pushValidation(Object validator,String fieldName,Map<String,String> param){
		InitField initField = null;
		try {
				if(validator!=null){
					if(validator instanceof FieldValidator){
						FieldValidator fieldValidator = (FieldValidator) validator;
						initField = new InitField(fieldName);
						if(validator instanceof com.opensymphony.xwork2.validator.validators.RequiredStringValidator 
								|| validator instanceof com.opensymphony.xwork2.validator.validators.RequiredFieldValidator){
							initField.setRequired(true);
						}
//						if(validator instanceof StringLengthFieldValidator ){
//							com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator lengthAnnotation = 
//								(com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator) annotation;
//							if(lengthAnnotation.minLength()!=null && !"".equals(lengthAnnotation.minLength())){
//								initField.setRequired(true);
//							}
//							fieldValidator.setValidatorType("length["+lengthAnnotation.minLength()+","+lengthAnnotation.maxLength()+"]");
//						}
						if(validator instanceof StringLengthFieldValidator ){
							if(!"".equals(param.get("minLength"))){
								if(Integer.parseInt(param.get("minLength"))!=0){
									initField.setRequired(true);
								}
							}
							fieldValidator.setValidatorType("length["+param.get("minLength")+","+param.get("maxLength")+"]");
						}
						initField.appendValidate(fieldValidator.getValidatorType());
					}
				}
			if(initField!=null){
				this.pushFieldValidate(initField);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	private void pushValidation(String fieldName,Annotation annotation){
		try {
			Object validator = this.getValidator(annotation);
			Map<String,String> annotationParameters = new HashMap<String, String>();
			Method[] ms = annotation.getClass().getDeclaredMethods();
			for (Method m: ms) {
				if(m.getName().equals("equals"))continue;
				annotationParameters.put(m.getName(), m.invoke(annotation, new Object[]{}).toString());
			}
			pushValidation(validator,fieldName,annotationParameters);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 获取该注解对应的校验器
	 * @param annotations
	 * @return
	 */
	private Object getValidator(Annotation annotation){

		Object o = null;
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
			o = ValidateInterceptor.getValidator(clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

}
