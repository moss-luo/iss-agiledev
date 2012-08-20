package com.isoftstone.agiledev.core.query.adaptors;

import java.util.Map;

import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.core.query.QueryParametersDbAdaptor;

public class HSqlQueryParametersAdaptor implements QueryParametersDbAdaptor {

	@Override
	public void adapt(QueryParameters parameters, Map<String, Object> parametersMap) {
		parametersMap.put("limit", parameters.getRows());
		parametersMap.put("offset", (parameters.getPage() - 1) * parameters.getRows());
		
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
