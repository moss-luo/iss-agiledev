package com.isoftstone.agiledev.validater;

import com.opensymphony.xwork2.validator.validators.RegexFieldValidator;

public class MobileValidator extends RegexFieldValidator{

	public static final String mobilePattern = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
//	@Override
//	public String validatePattern(Object obj) {
//		return "mobile";
//	}
//
//	@Override
//	public FieldError validate(String fieldName, Object value, Object pattern) {
//		Pattern p = Pattern.compile(mobilePattern);
//		Matcher m = p.matcher(value.toString());
//		if(!m.find()){
//			return new FieldError(fieldName,"form.mobileError");
//		}
//		return null;
//	}
	public MobileValidator() {
		setExpression(mobilePattern);
		setValidatorType("mobile");
	}
}
