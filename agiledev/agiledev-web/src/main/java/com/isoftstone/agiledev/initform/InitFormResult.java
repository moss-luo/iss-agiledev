package com.isoftstone.agiledev.initform;

import java.lang.reflect.Field;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 构造表单初始json数据
 * @author sinner
 *
 */
public class InitFormResult extends AbstractResult {

	private Object action = null;
	@Override
	public void doResult(HttpServletResponse response, ActionInvocation invocation) {
		action = invocation.getAction();
		Class<?> clazz = action.getClass();
		//处理初始数据
		while (clazz != Object.class) {
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if(field.isAnnotationPresent(Initialization.class)){
					Initialization initialization = field.getAnnotation(Initialization.class);
					if(initialization.value().length!=0){
						pluralHandle(field, initialization);
					}else{
						singularHandle(field);
					}
					//获取接口返回的初始值
					try {
						Class<?> targetInitializaObjectClazz = field.getType();
						Object targetInitializaObject = targetInitializaObjectClazz.newInstance();
						if(targetInitializaObject instanceof InitProvider){
							InitProvider provider = (InitProvider) targetInitializaObject;
							Map<String,Object> initValues = provider.initModel();
							if(initValues!=null && initValues.size()>0){
								for (String s : initValues.keySet()) {
									InitField initField = new InitField(s,initValues.get(s));
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
	}

	private void pluralHandle(Field field,Initialization init){
		Initializa[] inits = init.value();
		for (Initializa i : inits) {
			InitField initField = new InitField();
			String value = i.value();
			if(action instanceof ModelDriven){
				initField.setName(i.fieldName());
			}else{
				initField.setName(field.getName()+"."+i.fieldName());
				
			}
			initField.setInitValue(value);
			this.pushFieldInit(initField);
		}
	}
	private void singularHandle(Field field){
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
	}
}
