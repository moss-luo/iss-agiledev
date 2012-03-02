package com.isoftstone.agiledev.query;

import java.util.Properties;

import com.isoftstone.agiledev.query.QueryParameters;
import com.opensymphony.xwork2.ActionInvocation;

public interface QueryParametersInjector {
	void init(Properties properties);
	void inject(ActionInvocation invocation, QueryParameters parameters);
}
