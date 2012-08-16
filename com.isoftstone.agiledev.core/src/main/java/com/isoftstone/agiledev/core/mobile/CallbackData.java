package com.isoftstone.agiledev.core.mobile;

public class CallbackData {

	private String callback;
	private Object object;
	
	public CallbackData() {
		super();
	}
	public CallbackData(String callback, Object object) {
		super();
		this.callback = callback;
		this.object = object;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
}
