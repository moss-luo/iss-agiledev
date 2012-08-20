package com.isoftstone.agiledev.web.springmvc.binding;

import org.springframework.beans.MutablePropertyValues;

public interface BindingCallback {
	void beforeBinding(MutablePropertyValues mpvs, Object target);
}
