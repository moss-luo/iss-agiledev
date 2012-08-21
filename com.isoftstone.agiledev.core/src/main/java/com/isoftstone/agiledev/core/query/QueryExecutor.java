package com.isoftstone.agiledev.core.query;

import java.util.List;

public interface QueryExecutor<T> {
	public List<T> getResult(int start, int end, String orderBy);
	public int getTotal();
}
