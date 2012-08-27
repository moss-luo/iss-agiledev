package com.isoftstone.agiledev.core.validate;

public class ValidateData {
	private String annotationClass;
	private String expression;
	private ValidateParser parser;
	
	
	public String getAnnotationClass() {
		return annotationClass;
	}
	public void setAnnotationClass(String annotationClass) {
		this.annotationClass = annotationClass;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	public ValidateParser getParser() {
		return parser;
	}
	public void setParser(ValidateParser parser) {
		this.parser = parser;
	}
	
	
}
