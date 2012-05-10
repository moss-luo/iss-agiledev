package com.isoftstone.agiledev.actions.system.user;

import com.isoftstone.agiledev.initform.Initializa;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

public class User {

	private String uid;
	@Initializa("zhangsan")
	private String userId;
	private String password;
	private String photo;
	private String roleId;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String uid, String userId) {
		super();
		this.uid = uid;
		this.userId = userId;
	}
	private String roleName;
	
	//@RequiredStringValidator(trim=true,message="请输入用户名",fieldName="name")
	@StringLengthFieldValidator(message="用户名必须在1-12位之间",minLength="1",maxLength="12")
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@StringLengthFieldValidator(message="密码必须在1-10位之间",minLength="1",maxLength="10")
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPassword() {
		return password;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getUserId() {
		return userId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}