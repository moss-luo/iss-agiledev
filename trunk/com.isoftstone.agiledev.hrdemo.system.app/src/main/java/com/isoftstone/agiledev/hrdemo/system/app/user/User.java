package com.isoftstone.agiledev.hrdemo.system.app.user;

import com.isoftstone.agiledev.core.init.Init;
import com.isoftstone.agiledev.core.validate.annotation.Email;
import com.isoftstone.agiledev.core.validate.annotation.Mobile;

public class User {
	
	private Integer id;
	@Init(fieldName="name",value="zhangsan")
	private String name;
	@Init(fieldName="password",value="123123")
	private String password;
	@Mobile
	private String  mobile;
	@Email
	private String  email;
	
	
	public User(Integer id, String name, String password,String mobile,String email) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.mobile = mobile;
		this.email = email;
	}
	
	public User() {
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
