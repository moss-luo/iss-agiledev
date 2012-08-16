package com.isoftstone.agiledev.osgi.core.web.interceptor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.web.ActionInvocation;
import com.isoftstone.agiledev.osgi.core.web.Interceptor;
import com.isoftstone.agiledev.osgi.core.web.ValueStack;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestModel;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestParameter;

public class ParameterInterceptor implements Interceptor {

	private Logger logger = LoggerFactory.getLogger(ParameterInterceptor.class);
	@Override
	public void doInterceptor(ActionInvocation actionInvocation) {
		Object[] params = this.getActionParameters(actionInvocation);
		actionInvocation.setMethodParameter(params);
	}


	private Object[] getActionParameters(ActionInvocation invocation){
		Method method = invocation.getMethod();
		ValueStack stack = invocation.getStack();
		
		Annotation[][] as = method.getParameterAnnotations();
		Class<?>[] parametersType = method.getParameterTypes();
		List<Object> values = new ArrayList<Object>();
		for(int i=0;i<as.length;i++){
			Annotation[] annotation = as[i];
			if(annotation[0].annotationType().getName().equals(RequestParameter.class.getName())){
				String value = this.parameterHandler(stack, ((RequestParameter)annotation[0]));
				values.add(value);
			}else if(annotation[0].annotationType().getName().equals(RequestModel.class.getName())){
				Object value = this.modelHandler(stack,parametersType[i]);
				values.add(value);
			}
		}
		return values.toArray(new Object[]{});
	}
	/**
	 * 解析requestParameter类型的请求参数
	 * @param request
	 * @param annotation
	 * @return
	 */
	private String parameterHandler(ValueStack stack,RequestParameter annotation){
		return (String) stack.get(annotation.value());
	}
	
	/**
	 * 解析requestModel类型的请求参数
	 * @param request
	 * @param annotation
	 * @param parameterType
	 * @return
	 */
	private Object modelHandler(ValueStack stack,Class<?> parameterType){
		try {
			Object o = parameterType.newInstance();
			for (Field f : parameterType.getDeclaredFields()) {
				BeanUtils.setProperty(o, f.getName(), stack.get(f.getName()));
			}
			return o;
		} catch (Exception e) {
			logger.error("error in modelHandle", e);
		}
		return null;
	}
}
