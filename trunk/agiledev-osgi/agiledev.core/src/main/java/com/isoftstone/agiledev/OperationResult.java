package com.isoftstone.agiledev;

public class OperationResult {

	private boolean success;
	private String msg;
	public OperationResult() {
		super();
	}
	public OperationResult(boolean success) {
		super();
		this.success = success;
	}
	public OperationResult(boolean success, String msg) {
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
