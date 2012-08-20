package com.isoftstone.agiledev.core.query;

import java.util.List;
import java.util.Map;


public interface QueryByMapExecutor<T> {
	List<T> execute(Map<String, Object> parameters);
}
