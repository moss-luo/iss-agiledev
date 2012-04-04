package com.isoftstone.agiledev.actions.basedata.department;


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
	/**@StringLengthFieldValidator(message="部门名必须在1-10位之间",minLength="1",maxLength="10")
	 * 此注解为在有源码的情况下使用的注解，将其添加在需要进行校验的相关字段上。
	 * @param depName
	 */
	
	public void setDepName(String depName) {
		this.depName = depName;
	}
	
}
