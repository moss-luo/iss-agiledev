package com.isoftstone.agiledev.core.query;

import java.util.Map;

public interface QueryParametersDbAdaptor {
	Map<String, Object> adapt(int start, int end, String orderBy, Parameter[] parameters);
}
