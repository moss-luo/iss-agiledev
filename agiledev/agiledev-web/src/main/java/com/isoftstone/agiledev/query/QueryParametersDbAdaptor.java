package com.isoftstone.agiledev.query;

import java.util.Map;

import com.isoftstone.agiledev.query.QueryParameters;

public interface QueryParametersDbAdaptor {
	void adapt(QueryParameters parameters, Map<String, Object> parametersMap);
}
