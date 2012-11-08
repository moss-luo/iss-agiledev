package com.isoftstone.agiledev.web.ligerui;

import java.util.List;

public class LigerUITreeNode {
	private String text;
	private String url;
	private String icon;
	private boolean isexpend;
	private List<LigerUITreeNode> children;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public boolean isIsexpend() {
		return isexpend;
	}
	public void setIsexpend(boolean isexpend) {
		this.isexpend = isexpend;
	}
	public List<LigerUITreeNode> getChildren() {
		return children;
	}
	public void setChildren(List<LigerUITreeNode> children) {
		this.children = children;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
