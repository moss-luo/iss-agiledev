package com.isoftstone.agiledev.osgi.core.web.navigate;

import java.util.List;

public class Menu implements TreeSupport {

	private String id;
	private String text;
	private String url;
	private List<TreeSupport> children;
	private boolean hasChild;

	public enum State {
		open,
		closed
	}
	
	public Menu() {
		super();
	}
	public Menu(String id,String text, String url, List<TreeSupport> children) {
		super();
		this.id=id;
		this.text = text;
		this.url = url;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<TreeSupport> getChildren() {
		return children;
	}
	public void setChildren(List<TreeSupport> children) {
		this.children = children;
	}
	@Override
	public void setParentId(String pid) {
		
	}
	@Override
	public String getParentId() {
		return null;
	}
	@Override
	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}
	public State getState(){
		if(this.hasChild)
			return State.closed;
		return null;
	}
	
}
