package com.isoftstone.agiledev.core.init;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

public interface InitializeModelOutputAdaptor {

	void writeToInitForm(HttpServletResponse response,List<? extends InitField> initFields);
}
