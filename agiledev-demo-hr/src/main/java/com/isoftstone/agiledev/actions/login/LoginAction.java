package com.isoftstone.agiledev.actions.login;


import org.apache.struts2.convention.annotation.Result;

@Result(name="login", location="/main.html",type="redirect")
public class LoginAction {

	public String execute(){
		return "login";
	}
}
