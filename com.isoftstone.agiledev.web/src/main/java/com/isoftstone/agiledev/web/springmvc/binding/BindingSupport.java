package com.isoftstone.agiledev.web.springmvc.binding;

import org.springframework.beans.MutablePropertyValues;

public interface BindingSupport {
	void adjustProperties(MutablePropertyValues mpvs);
}
