package com.isoftstone.agiledev.core.query;

import java.util.Map;

public interface QueryParametersDbAdaptor {
	void adapt(QueryParameters parameters, Map<String, Object> parametersMap);
}
