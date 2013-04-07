package com.isoftstone.agiledev.web.springmvc.binding;

import org.springframework.beans.MutablePropertyValues;

import com.isoftstone.agiledev.core.ObjectFactory;
import com.isoftstone.agiledev.core.ObjectFactoryAware;
import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.web.springmvc.easyui.binding.EasyUIQueryParametersBindingSupport;

public class EasyUIBindingCallback implements BindingCallback, ObjectFactoryAware {
	private ObjectFactory objectFactory;
	
	@Override
	public void beforeBinding(MutablePropertyValues mpvs, Object target) {
		BindingSupport bindingSupport = getBindingSupport(target);
		if (bindingSupport != null) {
			bindingSupport.adjustProperties(mpvs);
		}
	}

	private BindingSupport getBindingSupport(Object target) {
		if (target instanceof QueryParameters) {
			return objectFactory.create(EasyUIQueryParametersBindingSupport.class);
		}
		
		return null;
	}

	@Override
	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}
}
