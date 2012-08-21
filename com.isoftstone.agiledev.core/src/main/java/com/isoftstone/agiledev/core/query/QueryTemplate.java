package com.isoftstone.agiledev.core.query;

public class QueryTemplate {
	
	public static <T> QueryResult<T> query(QueryParameters queryParameters, QueryExecutor<T> executor) {
		int page = queryParameters.getPage();
		int rows = queryParameters.getRows();
		
		if (page == QueryManager.NON_PAGEABLE) {
			page = 1;
			rows = Integer.MAX_VALUE;
		}
		
		int start = (page - 1) * rows;
		int end = page * rows;
		
		String orderBy = null;
		if (queryParameters.getSort() != null) {
			orderBy = queryParameters.getSort();
			
			if (queryParameters.getOrder() != null && "desc".equals(queryParameters.getOrder().toLowerCase())) {
				orderBy = String.format("%s %s", orderBy, " DESC");
			} else {
				orderBy = String.format("%s %s", orderBy, " ASC");
			}
		}
		
		return new QueryResult<T>(executor.getTotal(), executor.getResult(start, end, orderBy));
	}
}
