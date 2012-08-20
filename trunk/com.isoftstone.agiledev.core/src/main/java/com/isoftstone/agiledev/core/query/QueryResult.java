package com.isoftstone.agiledev.core.query;

import java.util.List;

public class QueryResult<T> {

	private int total;
	private List<T> data;
	
	public QueryResult(int total, List<T> data) {
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
