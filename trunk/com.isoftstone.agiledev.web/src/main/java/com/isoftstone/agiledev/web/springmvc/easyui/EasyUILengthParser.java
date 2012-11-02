package com.isoftstone.agiledev.web.springmvc.easyui;

import java.lang.annotation.Annotation;

import org.hibernate.validator.constraints.Length;

import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.validate.ValidateParser;

public class EasyUILengthParser implements ValidateParser {

	@Override
	public String getValidateExpression(Annotation annotation) {
		Length length = (Length) annotation;

		return new StringBuffer("length[").append(length.min()).append(",")
				.append(length.max()).append("]").append(";")
				.append(length.message())

				.toString();
	}

	@Override
	public InitField buildValidate(InitField initField, Annotation annotation) {
		String validateType = this.getValidateExpression(annotation);
		EasyUIInitField easyUIInitField = (EasyUIInitField) initField;
		easyUIInitField.setValidate(validateType.split(";")[0]);
		easyUIInitField.setDefMessage(validateType.split(";")[1]);

		Length length = (Length) annotation;
		if (length.min() > 0) {
			easyUIInitField.setRequired(true);
		}
		return easyUIInitField;
	}

}
