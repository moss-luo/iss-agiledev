package com.isoftstone.agiledev.validater;

import com.opensymphony.xwork2.validator.validators.RegexFieldValidator;

public class IDCardValidator extends RegexFieldValidator {

	public static final String IDCARD_PATTERN= "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65|71|81|82|91)\\d{4})((((19|20)(([02468][048])|([13579][26]))0229))|((20[0-9][0-9])|(19[0-9][0-9]))((((0[1-9])|(1[0-2]))((0[1-9])|(1\\d)|(2[0-8])))|((((0[1,3-9])|(1[0-2]))(29|30))|(((0[13578])|(1[02]))31))))((\\d{3}(x|X))|(\\d{4}))";

	public IDCardValidator() {
		setExpression(IDCARD_PATTERN);
		setValidatorType("idcard");
	}
	
//	@Override
//	public String validatePattern(Object obj) {
//		return "idCard";
//	}
//
//	@Override
//	public FieldError validate(String fieldName, Object value, Object pattern) {
//		Pattern p = Pattern.compile(IDCARD_PATTERN);  
//		Matcher m = p.matcher(value.toString());  
//		if(!m.find()){
//			return new FieldError(fieldName,"form.idCardError");
//		}
//		return null;
//	}

}
