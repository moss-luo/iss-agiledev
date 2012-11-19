package com.isoftstone.agiledev.web.springmvc.easyui;

import java.lang.annotation.Annotation;

import org.hibernate.validator.constraints.Email;

import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.validate.ValidateParser;

public class EasyUIEmailParser implements ValidateParser{

	@Override
	public String getValidateExpression(Annotation annotation) {
		Email email=(Email)annotation;
		return new StringBuffer("email").append(";")
				.append(email.message()).toString();
	}

	@Override
	public InitField buildValidate(InitField initField, Annotation annotation) {
		String validateType = this.getValidateExpression(annotation);
		EasyUIInitField easyUIInitField = (EasyUIInitField) initField;
		easyUIInitField.setValidate(validateType.split(";")[0]);
		easyUIInitField.setDefMessage(validateType.split(";")[1]);		
		return easyUIInitField;
	}
	

}
