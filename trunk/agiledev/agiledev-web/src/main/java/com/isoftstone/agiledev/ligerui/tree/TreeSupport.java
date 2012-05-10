package com.isoftstone.agiledev.ligerui.tree;

import java.util.List;

import com.isoftstone.agiledev.easyui.tree.Node.State;


public interface TreeSupport {

	String getId();
	void setId(String id);
	String getText();
	void setText(String text);
	String getUrl();
	void setUrl(String url);
	void hasChild(boolean has);
	State getState();
	void setParentId(String pid);
	String getParentId();
	List<TreeSupport> getChildren();
}
