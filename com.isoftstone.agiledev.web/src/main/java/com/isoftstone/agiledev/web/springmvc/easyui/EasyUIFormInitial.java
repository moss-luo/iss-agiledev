package com.isoftstone.agiledev.web.springmvc.easyui;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.core.init.AbstractInitializeSupport;
import com.isoftstone.agiledev.core.init.Init;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.Initialization;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;



public class EasyUIFormInitial extends AbstractInitializeSupport implements InitializeAdaptor {

	private Logger logger = LoggerFactory.getLogger(EasyUIFormInitial.class);
	@Override
	public void doInit(HttpServletRequest request,InitializeModel model) {
		try {
				EasyUIInitField initField = null;
				for (Class<?> clazz: model.getInitialiedType()) {
					Field[] fields = clazz.getDeclaredFields();
					for (Field field : fields) {
						if(field.isAnnotationPresent(Init.class)){
							Init initializa = field.getAnnotation(Init.class);
							initField = new EasyUIInitField();
							String name = field.getName();
							if(initializa.fieldName()!=null || !"".equals(initializa.fieldName())){
								name = initializa.fieldName();
							}
							initField.setName(name);
							initField.setInitValue(initializa.value());
							this.pushInitField(initField);
						}
					}
				}
			} catch (Exception e) {
				logger.error("error in FormInitial!",e);
			}
//		return this.initFields;
	}
	
	protected void pluralHandle(Field field,Initialization initialization){
		for (Init i : initialization.value()) {
			EasyUIInitField f = new EasyUIInitField();
			f.setName(i.fieldName());
			f.setInitValue(i.value());
			this.pushInitField(f);
		}
	}
	protected void singularHandle(Field field){
		try{
			Class<?> targetInitializaObjectClazz = field.getType();
			Field[] targetValidateObjectFields = targetInitializaObjectClazz.getDeclaredFields();
			for (Field initializaField : targetValidateObjectFields) {
				EasyUIInitField initField = null;
				//获取注解配置的初始值
				if(initializaField.isAnnotationPresent(Init.class)){
					Init init = initializaField.getAnnotation(Init.class);
					initField = new EasyUIInitField();
//					if(action instanceof ModelDriven){
//						initField.setName(initializaField.getName());
//					}else{
//						initField.setName(field.getName()+"."+initializaField.getName());
//						
//					}
					String name = initializaField.getName();
					if(init.fieldName()!=null || !"".equals(init.fieldName())){
						name = init.fieldName();
					}
					initField.setName(name);
					initField.setInitValue(init.value());
					this.pushInitField(initField);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pushInitField(EasyUIInitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			EasyUIInitField initField = (EasyUIInitField) field;
			initField.setInitValue(f.getInitValue());
		}else{
			this.addInitField(f);
		}
	}

}
