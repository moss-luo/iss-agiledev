package com.isoftstone.agiledev.actions.system.role;

import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

public class Role {

	private Integer uid;
	private String roleName;
	private String[] permisionId;

	public Role(Integer uid, String roleName) {
		this(uid,roleName,null);
	}
	
	public Role(Integer uid, String roleName, 
			String[] permisionId) {
		super();
		this.uid = uid;
		this.roleName = roleName;
		this.permisionId = permisionId;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getRoleName() {
		return roleName;
	}
	@StringLengthFieldValidator(fieldName="roleName",message="角色名称必须介于1到10之间",trim=true,minLength="1",maxLength="10")
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String[] getPermisionId() {
		return permisionId;
	}
	public void setPermisionId(String[] permisionId) {
		this.permisionId = permisionId;
	}
	
}
