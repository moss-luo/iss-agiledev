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
	@StringLengthFieldValidator(message="部门名必须在1-10位之间",minLength="1",maxLength="10")
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
}
