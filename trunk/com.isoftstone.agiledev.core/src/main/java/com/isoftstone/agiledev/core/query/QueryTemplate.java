package com.isoftstone.agiledev.core.query;

import java.util.HashMap;
import java.util.Map;

public class QueryTemplate {
	private QueryParametersDbAdaptor dbAdaptor;
	
	public QueryTemplate() {}
	
	public QueryTemplate(QueryParametersDbAdaptor dbAdaptor) {
		this.dbAdaptor = dbAdaptor;
	}
	
	public <T> QueryResult<T> query(SummaryProvider summaryProvider, QueryParameters queryParameters,
			QueryExecutor<T> executor) {
		return query(new QueryParametersManager(summaryProvider, queryParameters), executor);
	}
	
	private class QueryParametersManager extends AbstractQueryManager {
		private SummaryProvider summaryProvider;
		
		public QueryParametersManager(SummaryProvider summaryProvider, QueryParameters queryParameters) {
			this.summaryProvider = summaryProvider;
		}

		@Override
		public int getTotal() {
			return summaryProvider.getTotal();
		}
	}
	
	public <T> QueryResult<T> query(QueryManager queryManager, QueryExecutor<T> executor) {
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
		
		return new QueryResult<T>(queryManager.getTotal(), executor.execute(start, end, orderBy));
	}
	
	public <T> QueryResult<T> queryByMap(SummaryProvider summaryProvider, QueryParameters queryParameters,
			QueryByMapExecutor<T> executor) {
		Map<String, Object> parametersMap = new HashMap<String, Object>();
		if (dbAdaptor != null) {
			dbAdaptor.adapt(queryParameters, parametersMap);
		} else {
			parametersMap.put("page", queryParameters.getPage());
			parametersMap.put("rows", queryParameters.getRows());
			parametersMap.put("order", queryParameters.getOrder());
			parametersMap.put("sort", queryParameters.getSort());
		}
		
		return new QueryResult<T>(summaryProvider.getTotal(), executor.execute(parametersMap));
	}
}
