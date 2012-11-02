package com.isoftstone.agiledev.web.springmvc.easyui;

public class BindResultField {
	private String field;
	private String defMessage;
	private Object rejectedValue;
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getDefMessage() {
		return defMessage;
	}
	public void setDefMessage(String defMessage) {
		this.defMessage = defMessage;
	}
	public Object getRejectedValue() {
		return rejectedValue;
	}
	public void setRejectedValue(Object rejectedValue) {
		this.rejectedValue = rejectedValue;
	}

}
