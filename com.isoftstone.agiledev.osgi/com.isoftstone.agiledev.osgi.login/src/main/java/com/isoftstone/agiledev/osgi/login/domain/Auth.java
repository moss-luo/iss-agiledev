package com.isoftstone.agiledev.osgi.login.domain;

import com.isoftstone.agiledev.osgi.core.domain.EntityAlias;

@EntityAlias("Auth")
public class Auth {

	private String id;
	private String loginName;
	private String loginPassword;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
}
