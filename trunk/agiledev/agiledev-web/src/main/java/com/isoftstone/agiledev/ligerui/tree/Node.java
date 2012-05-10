package com.isoftstone.agiledev.ligerui.tree;

import java.util.List;

import com.isoftstone.agiledev.easyui.tree.Node.State;

public class Node implements TreeSupport{

	private String id;
	private String pid;
	private String text;
	private String url;
	private List<TreeSupport> children;
	private boolean isexpand;
	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	public List<TreeSupport> getChildren() {
		return this.children;
	}

	public boolean isIsexpand() {
		return isexpand;
	}

	public void setIsexpand(boolean isexpand) {
		this.isexpand = isexpand;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public void setChildren(List<TreeSupport> children) {
		this.children = children;
	}

	@Override
	public void hasChild(boolean has) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public State getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setParentId(String pid) {
		this.pid = pid;
	}

	@Override
	public String getParentId() {
		return this.pid;
	}

}
