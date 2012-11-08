package com.isoftstone.agiledev.core.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private String id;
	private String text;
	private List<Node> children;
	private String url;
	private boolean hasChild;
	
	public Node(String id, String text) {
		this(id, text, false);
	}
	
	public Node(String id, String text, boolean hasChild) {
		this(id, text, hasChild, null);
	}
	
	public Node(String id, String text, boolean hasChild, String url) {
		this.id = id;
		this.text = text;
		this.hasChild = hasChild;
		this.url = url;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Node> getChildren() {
		return this.children;
	}

	public void setParentId(String pid) {
		
	}

	public String getParentId() {
		return null;
	}
	
	public Node addChild(Node child) {
		if (child == null)
			throw new IllegalArgumentException("Null node");
		
		if (children == null) {
			children = new ArrayList<Node>();
		}
		
		children.add(child);
		
		return this;
	}
	
	public boolean hasChild() {
		return hasChild;
	}
	
	public boolean isHasChild() {
		return hasChild;
	}
	
	public boolean getHasChild() {
		return hasChild;
	}
	
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
}
