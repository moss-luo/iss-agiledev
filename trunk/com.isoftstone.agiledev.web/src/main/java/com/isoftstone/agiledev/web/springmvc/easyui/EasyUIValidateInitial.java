package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.isoftstone.agiledev.core.init.AbstractInitializeSupport;
import com.isoftstone.agiledev.core.init.InitChain;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;

public class EasyUIValidateInitial extends AbstractInitializeSupport implements InitializeAdaptor {

	@Override
	public List<InitField> doInit(HttpServletRequest request,InitializeModel model,InitChain initChain) {
		
		return this.initFields;
	}

}
