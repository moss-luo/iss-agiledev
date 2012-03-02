package com.isoftstone.agiledev.query;

public class QueryUtils {
	public static <T> T query(QueryManager queryManager, QueryExecutor<T> executor) {
		int page = queryManager.getPage();
		int rows = queryManager.getRows();
		
		if (page == QueryManager.NON_PAGEABLE) {
			page = 1;
			rows = Integer.MAX_VALUE;
		}
		
		int start = (page - 1) * rows;
		int end = page * rows;
		
		String orderBy = null;
		if (queryManager.getSort() != null) {
			orderBy = queryManager.getSort();
			
			if (queryManager.getOrder() != null && "desc".equals(queryManager.getOrder().toLowerCase())) {
				orderBy = String.format("%s %s", orderBy, " DESC");
			} else {
				orderBy = String.format("%s %s", orderBy, " ASC");
			}
		}
		
		return executor.execute(start, end, orderBy);
	}
}
