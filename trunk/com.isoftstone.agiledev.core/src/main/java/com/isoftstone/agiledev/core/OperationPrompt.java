package com.isoftstone.agiledev.core;

public class OperationPrompt {
	private boolean success;
	private String msg;
	
	public OperationPrompt() {
	}
	
	public OperationPrompt(String msg) {
		this.msg = msg;
	}
	
	public OperationPrompt(boolean success) {
		this(null, success);
	}
	
	public OperationPrompt(String msg, boolean success) {
		this.success = success;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
