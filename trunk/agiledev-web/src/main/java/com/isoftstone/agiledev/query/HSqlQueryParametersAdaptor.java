package com.isoftstone.agiledev.query;

import java.util.Map;

import com.isoftstone.agiledev.query.QueryParameters;

public class HSqlQueryParametersAdaptor implements QueryParametersDbAdaptor {

	@Override
	public void adapt(QueryParameters parameters, Map<String, Object> parametersMap) {
		parametersMap.put("offset", parameters.getRows());
		parametersMap.put("limit", (parameters.getPage() - 1) * parameters.getRows());
		
		String orderBy = null;
		if (parameters.getSort() != null) {
			orderBy = parameters.getSort();
			
			if (parameters.getOrder() != null && "desc".equals(parameters.getOrder().toLowerCase())) {
				orderBy = String.format("%s %s", orderBy, " DESC");
			} else {
				orderBy = String.format("%s %s", orderBy, " ASC");
			}
			
			parametersMap.put("orderBy", orderBy);
		}
	}

}
