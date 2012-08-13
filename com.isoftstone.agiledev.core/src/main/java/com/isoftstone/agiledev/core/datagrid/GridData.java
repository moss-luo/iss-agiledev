package com.isoftstone.agiledev.core.datagrid;

import java.util.List;

public class GridData<T> {

	private int total;
	private List<T> data;
	
	public GridData(int total, List<T> data) {
		this.total = total;
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public List<T> getData() {
		return data;
	}
	
}
