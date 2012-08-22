package com.isoftstone.agiledev.core.query.mybatis;

import java.util.HashMap;
import java.util.Map;

import com.isoftstone.agiledev.core.query.Parameter;

public abstract class AbstractParametersMapBuilder implements ParametersMapBuilder {
	@Override
	public Map<String, Object> build(int start, int end, String orderBy,
			Parameter parameter) {
		return build(start, end, orderBy, new Parameter[] {parameter});
	}
	
	@Override
	public Map<String, Object> build(int start, int end, String orderBy, Parameter[] parameters) {
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
