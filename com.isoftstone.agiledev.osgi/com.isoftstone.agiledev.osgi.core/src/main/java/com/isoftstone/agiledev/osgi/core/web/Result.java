package com.isoftstone.agiledev.osgi.core.web;

public class Result {
	private boolean success;
	private String msg;
	
	public Result() {
		super();
	}
	public Result(boolean success, String msg) {
		super();
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
