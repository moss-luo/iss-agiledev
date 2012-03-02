package com.isoftstone.agiledev.query;

import java.util.Properties;

import com.isoftstone.agiledev.query.QueryManager;
import com.isoftstone.agiledev.query.QueryParameters;
import com.opensymphony.xwork2.ActionInvocation;

public class QueryManagerInjector implements QueryParametersInjector {

	@Override
	public void inject(ActionInvocation invocation, QueryParameters parameters) {
		Object action = invocation.getAction();
		if (action instanceof QuerySupportAction) {
			QueryManager queryManager = ((QuerySupportAction)action).getQueryManager();
			
			queryManager.setPage(parameters.getPage());
			queryManager.setRows(parameters.getRows());
			queryManager.setSort(parameters.getSort());
			queryManager.setOrder(parameters.getOrder());
			
		}
	}

	@Override
	public void init(Properties properties) {}

}
