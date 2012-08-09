package com.isoftstone.agiledev.core.datagrid;

import java.util.List;

public class GridData {

	private int total;
	private List<?> data;
	
	public GridData(int total, List<?> data) {
		super();
		this.total = total;
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public List<?> getData() {
		return data;
	}
	
}
