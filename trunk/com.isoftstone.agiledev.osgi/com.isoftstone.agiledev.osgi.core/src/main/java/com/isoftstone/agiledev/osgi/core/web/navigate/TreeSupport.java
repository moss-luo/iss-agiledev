package com.isoftstone.agiledev.osgi.core.web.navigate;

import java.util.List;

import com.isoftstone.agiledev.osgi.core.web.navigate.Menu.State;




public interface TreeSupport {

	String getId();
	void setId(String id);
	
	String getText();
	void setText(String text);
	
	String getUrl();
	void setUrl(String url);
	
	void setParentId(String pid);
	String getParentId();

	void setHasChild(boolean hasChild);
	State getState();
	
	List<TreeSupport> getChildren();
}
