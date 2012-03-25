package org.sinner.study.controller;

public class Result {

	private boolean success;
	private String msg;
	public Result() {
		super();
	}
	public Result(boolean success) {
		this(success,null);
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
