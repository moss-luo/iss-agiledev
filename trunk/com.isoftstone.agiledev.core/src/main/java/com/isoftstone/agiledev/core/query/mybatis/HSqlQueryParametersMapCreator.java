package com.isoftstone.agiledev.core.query.mybatis;

import java.util.Map;

public class HSqlQueryParametersMapCreator extends AbstractQueryParametersMapCreator {

	protected void adaptQueryParameters(int start, int end, String orderBy, Map<String, Object> parametersMap) {
		parametersMap.put("limit", end - start);
		parametersMap.put("offset", start);
		parametersMap.put("orderBy", orderBy);
	}

}
