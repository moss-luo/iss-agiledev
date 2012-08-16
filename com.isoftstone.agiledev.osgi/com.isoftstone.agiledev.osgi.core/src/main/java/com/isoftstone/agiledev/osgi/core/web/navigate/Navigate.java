package com.isoftstone.agiledev.osgi.core.web.navigate;

import java.util.List;


public class Navigate {

	private String name;
	private List<TreeSupport> menus;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<TreeSupport> getMenus() {
		return menus;
	}

	public void setMenus(List<TreeSupport> menus) {
		this.menus = menus;
	}
}
