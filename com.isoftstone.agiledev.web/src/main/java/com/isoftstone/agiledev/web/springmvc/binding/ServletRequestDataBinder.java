package com.isoftstone.agiledev.web.springmvc.binding;

import java.util.List;

import org.springframework.beans.MutablePropertyValues;

public class ServletRequestDataBinder extends org.springframework.web.bind.ServletRequestDataBinder {
	private List<BindingCallback> bindingCallbacks;
	
	public ServletRequestDataBinder(List<BindingCallback> bindingCallbacks, Object target, String objectName) {
		super(target, objectName);
		this.bindingCallbacks = bindingCallbacks;
	}

	@Override
	protected void doBind(MutablePropertyValues mpvs) {
		beforeBinding(mpvs);
		super.doBind(mpvs);
	}

	protected void beforeBinding(MutablePropertyValues mpvs) {
		for (BindingCallback cb : bindingCallbacks) {
			cb.beforeBinding(mpvs, getTarget());
		}
	}

}
