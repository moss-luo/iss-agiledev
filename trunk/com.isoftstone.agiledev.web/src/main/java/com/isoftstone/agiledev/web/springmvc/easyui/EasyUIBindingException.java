package com.isoftstone.agiledev.web.springmvc.easyui;

public class EasyUIBindingException {
private boolean isValid=true;
private BindResultField[] fields;
public boolean isValid() {
	return isValid;
}
public void setValid(boolean isValid) {
	this.isValid = isValid;
}
public BindResultField[] getFields() {
	return fields;
}
public void setFields(BindResultField[] fields) {
	this.fields = fields;
}


}
