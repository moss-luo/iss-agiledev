package com.isoftstone.agiledev.actions.basedata.department;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

public class Department {

	private String uid;
	private String depName;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDepName() {
		return depName;
	}
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
}
