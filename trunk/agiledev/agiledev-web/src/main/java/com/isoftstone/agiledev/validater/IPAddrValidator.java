package com.isoftstone.agiledev.validater;

import com.opensymphony.xwork2.validator.validators.RegexFieldValidator;

public class IPAddrValidator extends RegexFieldValidator{

	public IPAddrValidator() {
		setExpression(ippattern);
		setValidatorType("ipaddress");
	}
	public static final String ippattern = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";
//	@Override
//	public String validatePattern(Object obj) {
//		return "ipAddress";
//	}
//
//	@Override
//	public FieldError validate(String fieldName, Object value, Object pattern) {
//		Pattern p = Pattern.compile(ippattern);
//		Matcher m = p.matcher(value.toString());
//		if(!m.find()){
//			return new FieldError(fieldName,"form.ipAddressError");
//		}
//		return null;
//	}
}
