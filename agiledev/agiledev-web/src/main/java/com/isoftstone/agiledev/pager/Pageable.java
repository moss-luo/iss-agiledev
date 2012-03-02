package com.isoftstone.agiledev.pager;

public interface Pageable {
	public static final int NON_PAGEABLE = -1;
	
	void setPage(int page);
	void setRows(int rows);
	int getPage();
	int getRows();
}
