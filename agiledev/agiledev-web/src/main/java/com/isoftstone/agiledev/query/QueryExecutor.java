package com.isoftstone.agiledev.query;

public interface QueryExecutor<T> {
	public T execute(int start, int end, String orderBy);
}
