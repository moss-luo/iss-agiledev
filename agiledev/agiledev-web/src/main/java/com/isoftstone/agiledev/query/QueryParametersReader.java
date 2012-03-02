package com.isoftstone.agiledev.query;

import java.util.Map;
import java.util.Properties;

import com.isoftstone.agiledev.query.QueryParameters;

public interface QueryParametersReader {
	void init(Properties properties);
	QueryParameters readQueryParameters(Map<String, Object> parameters);
}
