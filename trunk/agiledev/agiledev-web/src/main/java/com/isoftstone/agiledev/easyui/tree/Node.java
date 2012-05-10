package com.isoftstone.agiledev.easyui.tree;

import java.util.List;

import com.isoftstone.agiledev.ligerui.tree.TreeSupport;

public class Node implements TreeSupport{
	private String id;
	private String text;
	private List<TreeSupport> children;
	private String url;
	private boolean hasChild;
	public enum State {
		open,
		closed
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getText() {
		return "<a hh='"+this.getUrl()+"'>"+this.text+"</a>";
	}

	@Override
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String getUrl() {
		return this.url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void hasChild(boolean has) {
		this.hasChild = has;
	}

	@Override
	public State getState() {
		return this.hasChild?null:State.closed;
	}

	@Override
	public List<TreeSupport> getChildren() {
		return this.children;
	}

	@Override
	public void setParentId(String pid) {
		
	}

	@Override
	public String getParentId() {
		return null;
	}
}
