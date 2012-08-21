package com.isoftstone.agiledev.core.query.adaptors;

import java.util.HashMap;
import java.util.Map;

import com.isoftstone.agiledev.core.query.Parameter;
import com.isoftstone.agiledev.core.query.QueryParametersDbAdaptor;

public abstract class AbstractQueryParametersDbAdaptor implements QueryParametersDbAdaptor {
	@Override
	public Map<String, Object> adapt(int start, int end, String orderBy, Parameter[] parameters) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		adaptQueryParameters(start, end, orderBy, parametersMap);
		
		if (parameters != null) {
			for (Parameter parameter : parameters) {
				parametersMap.put(parameter.getName(), parameter.getValue());
			}
		}
		
		return parametersMap;
	}

	protected abstract void adaptQueryParameters(int start, int end, String orderBy,
			Map<String, Object> parametersMap);
}
