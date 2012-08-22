package com.isoftstone.agiledev.core.query.mybatis;

import java.util.Map;

import com.isoftstone.agiledev.core.query.Parameter;

public interface ParametersMapBuilder {
	Map<String, Object> build(int start, int end, String orderBy, Parameter parameter);
	Map<String, Object> build(int start, int end, String orderBy, Parameter[] parameters);
}
