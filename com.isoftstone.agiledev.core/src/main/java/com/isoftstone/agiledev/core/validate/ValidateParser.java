package com.isoftstone.agiledev.core.validate;

import java.lang.annotation.Annotation;

import com.isoftstone.agiledev.core.init.InitField;

public interface ValidateParser {

	String getValidateExpression(Annotation annotation);
	InitField buildValidate(InitField initField,Annotation annotation);
	
}
