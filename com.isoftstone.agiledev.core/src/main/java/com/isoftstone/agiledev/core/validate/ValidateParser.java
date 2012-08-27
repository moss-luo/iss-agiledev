package com.isoftstone.agiledev.core.validate;

import java.lang.annotation.Annotation;

public interface ValidateParser {

	String getValidateExpression(Annotation annotation);
	
}
