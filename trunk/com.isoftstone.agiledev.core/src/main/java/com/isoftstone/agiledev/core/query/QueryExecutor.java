package com.isoftstone.agiledev.core.query;

import java.util.List;

public interface QueryExecutor<T> {
	public List<T> execute(int start, int end, String orderBy);
}
