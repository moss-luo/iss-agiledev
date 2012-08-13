package com.isoftstone.agiledev.core.query;

import java.util.Map;
import java.util.Properties;

public interface QueryParametersReader {
	void init(Properties properties);
	QueryParameters readQueryParameters(Map<String, Object> parameters);
}
