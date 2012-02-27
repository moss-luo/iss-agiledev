package com.isoftstone.agiledev.pager.easyui;

import java.util.List;

import com.isoftstone.agiledev.pager.PageResult;

public class EasyPageResult implements PageResult {
	
	public static final String TOTAL_KEY = "total";
	
	public static final String RESULT_ROWS_KEY = "rows";
	
	private int total;
	
	private List<Object> rows;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}
}
