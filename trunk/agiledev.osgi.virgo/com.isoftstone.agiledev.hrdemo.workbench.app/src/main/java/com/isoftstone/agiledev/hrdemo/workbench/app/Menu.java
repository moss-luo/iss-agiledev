package com.isoftstone.agiledev.hrdemo.workbench.app;

import java.util.List;

public class Menu implements Comparable<Menu> {
	private String id;
	private String parentId;
	private String text;
	private String icon;
	private String action;
	private List<Menu> children;
	private boolean hasChild;
	private int order;
	
	public Menu(String id) {
		this.id = id;
		this.hasChild = false;
		this.order = 0;
	}
	
	public List<Menu> getChildren() {
		return children;
	}

	public void setChildren(List<Menu> children) {
		this.children = children;
	}

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
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getAction() {
		return action;
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
	public String getParentId() {
		return parentId;
	}
	
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	
	public boolean getHasChild() {
		return hasChild;
	}
	
	public void setOrder(int order) {
		this.order = order;
	}
	
	public int getOrder() {
		return order;
	}
	
	@Override
	public int hashCode() {
		if (id == null)
			return 0;
		
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Menu) {
			Menu other = (Menu)obj;
			
			if (this.id == null)
				return other.id == null;
			
			return this.id.equals(other.id);
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Menu[");
		builder.append("id=").append(id).append(",");
		builder.append("parentId=").append(parentId).append(",");
		builder.append("text=").append(text).append(",");
		builder.append("icon=").append(icon).append(",");
		builder.append("action=").append(action).append(",");
		builder.append("children.size=").append(children == null ? 0 : children.size());
		builder.append("]");
		
		return builder.toString();
	}

	@Override
	public int compareTo(Menu o) {
		return this.order - o.order;
	}
	
}
