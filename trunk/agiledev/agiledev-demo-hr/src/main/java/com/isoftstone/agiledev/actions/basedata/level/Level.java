package com.isoftstone.agiledev.actions.basedata.level;


public class Level {

	private String uid;
	private String levelName;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getLevelName() {
		return levelName;
	}
	//@StringLengthFieldValidator(message="职级名称必须在1-10之间",minLength="1",maxLength="10")
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}
