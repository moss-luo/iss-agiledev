package com.isoftstone.agiledev.validater;

import com.opensymphony.xwork2.validator.validators.RegexFieldValidator;

public class ErrorCharValidator extends RegexFieldValidator {

	public static final String ERROR_CHAR_REG = "";
	public ErrorCharValidator() {
		setExpression(ERROR_CHAR_REG);
		setCaseSensitive(false);
		this.setValidatorType("errorChar");
	}

}
