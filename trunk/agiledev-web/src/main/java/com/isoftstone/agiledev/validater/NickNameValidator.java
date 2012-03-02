package com.isoftstone.agiledev.validater;

import com.opensymphony.xwork2.validator.validators.RegexFieldValidator;

public class NickNameValidator extends RegexFieldValidator {

	public NickNameValidator() {
		setExpression(nnReg);
		setValidatorType("nickname");
	}

	public static final String nnReg = "[a-zA-Z0-9_\\u4e00-\\u9fa5]*[a-zA-Z_\\u4e00-\\u9fa5]+[a-zA-Z0-9_\\u4e00-\\u9fa5]*";
//	@Override
//	public String validatePattern(Object obj) {
//		return "nickName";
//	}
//
//	@Override
//	public FieldError validate(String fieldName, Object value, Object pattern) {
//		   Pattern p = Pattern.compile(nnReg);
//		   Matcher isNum = p.matcher(value.toString());
//		   if(!isNum.matches()){
//			   return new FieldError(fieldName,"form.nickNameError");
//		   }
//		   return null;
//	}

}
