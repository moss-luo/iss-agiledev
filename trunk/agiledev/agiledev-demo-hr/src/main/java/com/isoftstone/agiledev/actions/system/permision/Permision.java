package com.isoftstone.agiledev.actions.system.permision;

public class Permision {

	private String uid;
	private String permisionName;
	private String url;
	private String pid;
	private Integer hasChild;
	
	public Permision() {
		super();
	}
	public Permision(String uid, String permisionName, String url) {
		super();
		this.uid = uid;
		this.permisionName = permisionName;
		this.url = url;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPermisionName() {
		return permisionName;
	}
	public void setPermisionName(String permisionName) {
		this.permisionName = permisionName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public Integer getHasChild() {
		return hasChild;
	}
	public void setHasChild(Integer hasChild) {
		this.hasChild = hasChild;
	}
}