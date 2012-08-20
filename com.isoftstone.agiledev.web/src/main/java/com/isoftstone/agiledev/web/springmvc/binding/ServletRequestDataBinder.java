package com.isoftstone.agiledev.web.springmvc.binding;

import org.springframework.beans.MutablePropertyValues;

import com.isoftstone.agiledev.core.ObjectFactory;

public class ServletRequestDataBinder extends org.springframework.web.bind.ServletRequestDataBinder {
	private ObjectFactory objFactory;
	
	public ServletRequestDataBinder(ObjectFactory objFactory, Object target, String objectName) {
		super(target, objectName);
		this.objFactory = objFactory;
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
		BindingCallback bs = getBindingCallback();
		if (bs != null) {
			bs.beforeBinding(mpvs, getTarget());
		}
	}

	private BindingCallback getBindingCallback() {
		// TODO support other JavaScript frameworks.
		return new EasyUIBindingCallback(objFactory);
	}
}
