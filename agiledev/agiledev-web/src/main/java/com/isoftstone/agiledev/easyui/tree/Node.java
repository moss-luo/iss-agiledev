package com.isoftstone.agiledev.easyui.tree;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private String id;
	private String text;
	private State state;
	private boolean checked;
	private boolean selected;
	private List<Node> children;
	private String href;
	
	public enum State {
		open,
		closed
	}
	
	public Node() {
		this(null,null);
	}

	public Node(String id, String text) {
		this(id, text, null);
	}
	public Node(String id, String text,String href) {
		this(id, text, href,State.open);
	}
	
	public Node(String id, String text,String href,State state) {
		this(id, text, href,state,false);
	}

	public Node(String id, String text, String href,State state, boolean checked) {
		this(id,text,href,state,checked,false);
	}
	public Node(String id, String text, String href,State state, boolean checked,boolean selected) {
		this(id,text,href,state,checked,selected,null);
	}
	
	public Node(String id, String text,String href, State state, boolean checked,boolean selected,List<Node> children) {
		this.id = id;
		this.text = text;
		this.state = state;
		this.checked = checked;
		this.href = href;
		this.children = children;
		this.selected = selected;
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
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public boolean isChecked() {
		return checked;
	}
	
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public List<Node> getChildren() {
		if (children == null) {
			children = new ArrayList<Node>();
		}
		
		return children;
	}
	
	public void setChildren(List<Node> children) {
		this.children = children;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Node) {
			Node other = (Node)obj;
			if (other == null || other.id == null ||
					!other.id.equals(this.id)) {
				return false;
			}
		}
		
		return this == obj;
	}
	
	@Override
	public String toString() {
		return String.format("[id: %s, text: %s, state: %s, checked: %b, children: %s]",
				id, text, state, checked, getChildrenString());
	}

	private String getChildrenString() {
		if (children == null || children.size() == 0) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		for (Node child : children) {
			sb.append(child.toString());
		}
		
		return sb.toString();
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
