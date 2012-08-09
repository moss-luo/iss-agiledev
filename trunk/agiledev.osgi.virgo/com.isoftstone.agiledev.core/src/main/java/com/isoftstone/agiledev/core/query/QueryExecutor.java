package com.isoftstone.agiledev.core.query;

public interface QueryExecutor<T> {
	public T execute(int start, int end, String orderBy);
}
