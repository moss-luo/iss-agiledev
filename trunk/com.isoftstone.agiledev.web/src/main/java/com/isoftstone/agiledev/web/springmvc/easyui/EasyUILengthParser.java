package com.isoftstone.agiledev.web.springmvc.easyui;

import java.lang.annotation.Annotation;

import org.hibernate.validator.constraints.Length;

import com.isoftstone.agiledev.core.validate.ValidateParser;

public class EasyUILengthParser implements ValidateParser {

	@Override
	public String getValidateExpression(Annotation annotation) {
		Length length = (Length) annotation;
		return new StringBuffer("length[")
					.append(length.min())
						.append(",")
							.append(length.max())
								.append("]")
									.toString();
	}

}
