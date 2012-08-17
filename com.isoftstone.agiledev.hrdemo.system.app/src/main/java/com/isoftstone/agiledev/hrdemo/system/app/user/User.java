package com.isoftstone.agiledev.hrdemo.system.app.user;

import com.isoftstone.agiledev.core.init.Init;

public class User {
	
	private Integer id;
	@Init(fieldName="name",value="zhangsan")
	private String name;
	@Init(fieldName="password",value="123123")
	private String password;
	
	
	public User(Integer id, String name, String password) {
		this.id = id;
		this.name = name;
		this.password = password;
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
}
