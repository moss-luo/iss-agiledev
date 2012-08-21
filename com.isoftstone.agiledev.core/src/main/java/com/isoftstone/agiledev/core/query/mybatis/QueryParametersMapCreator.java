package com.isoftstone.agiledev.core.query.mybatis;

import java.util.Map;

import com.isoftstone.agiledev.core.query.Parameter;

public interface QueryParametersMapCreator {
	Map<String, Object> create(int start, int end, String orderBy, Parameter parameter);
	Map<String, Object> create(int start, int end, String orderBy, Parameter[] parameters);
}
