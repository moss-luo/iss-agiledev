package com.isoftstone.agiledev.hrdemo.workbench.app;

import java.util.List;
import java.util.Locale;

public interface IMenuManager {
	List<Menu> getMenus(String parentId);
	void setLocale(Locale locale);
	Locale getLocale();
}
