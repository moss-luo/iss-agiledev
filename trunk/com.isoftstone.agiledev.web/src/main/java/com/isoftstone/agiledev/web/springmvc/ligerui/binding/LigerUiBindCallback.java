package com.isoftstone.agiledev.web.springmvc.ligerui.binding;

import org.springframework.beans.MutablePropertyValues;

import com.isoftstone.agiledev.core.ObjectFactory;
import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.web.springmvc.binding.BindingCallback;
import com.isoftstone.agiledev.web.springmvc.binding.BindingSupport;

public class LigerUiBindCallback implements BindingCallback{

	private ObjectFactory objFactory;
	
	public LigerUiBindCallback(ObjectFactory objFactory) {
		this.objFactory = objFactory;
	}
	
	@Override
	public void beforeBinding(MutablePropertyValues mpvs, Object target) {
		BindingSupport s = this.getBindingSupport(target);
		if (s != null) {
			s.adjustProperties(mpvs);
		}
	}
	

	private BindingSupport getBindingSupport(Object target) {
		if (target instanceof QueryParameters) {
			return objFactory.create(LigerUIQueryParametersBindingSupport.class);
		}
		
		return null;
	}

}
