package com.isoftstone.agiledev.web.springmvc.binding;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;

import com.isoftstone.agiledev.config.ConfigReaderFactory;
import com.isoftstone.agiledev.core.ObjectFactory;

public class ServletRequestDataBinder extends org.springframework.web.bind.ServletRequestDataBinder {
	private ObjectFactory objFactory;
	List<BindingCallback> bindingCallbacks = new ArrayList<BindingCallback>();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ServletRequestDataBinder(ObjectFactory objFactory, Object target, String objectName) {
		super(target, objectName);
		this.objFactory = objFactory;
		List<String> callbacks = ConfigReaderFactory.readCallbacks();
		Class[] parameterTypes = {ObjectFactory.class};
		Object[] parameters = {objFactory};
		try{
			for(String callback : callbacks)
			{
				Class c = Class.forName(callback);
				Constructor constructor = c.getConstructor(parameterTypes);
				Object o = constructor.newInstance(parameters);
				if(o instanceof BindingCallback)
				{
					bindingCallbacks.add((BindingCallback)o);
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public ServletRequestDataBinder(Object target) {
		super(target);
	}
	
	@Override
	protected void doBind(MutablePropertyValues mpvs) {
		beforeBinding(mpvs);
		super.doBind(mpvs);
	}

	protected void beforeBinding(MutablePropertyValues mpvs) {
		if(bindingCallbacks.isEmpty())
			new EasyUIBindingCallback(objFactory).beforeBinding(mpvs, getTarget());
		else
		{
			for (BindingCallback cb : bindingCallbacks) {
				cb.beforeBinding(mpvs, getTarget());
			}
		}
	}

}
