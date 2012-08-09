package com.isoftstone.agiledev.web.easyui;

import java.util.List;

public class EasyUITreeNode {
	private String id;
	private String text;
	private List<EasyUITreeNode> children;
	private String state;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public List<EasyUITreeNode> getChildren() {
		return children;
	}
	
	public void setChildren(List<EasyUITreeNode> children) {
		this.children = children;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
}
