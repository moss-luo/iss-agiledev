package com.isoftstone.agiledev.pager.interceptor;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import com.isoftstone.agiledev.pager.PageFactory;
import com.isoftstone.agiledev.pager.PaginationInterface;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;

@SuppressWarnings("serial")
public class PageInterceptor extends MethodFilterInterceptor {
	
	private String strategy;
	
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	private Set<String> queryKey = Collections.emptySet();
	
	public Set<String> getQueryKey() {
		return queryKey;
	}

	public void setQueryKey(String queryKey) {
		this.queryKey = this.asCollection(queryKey);
	}


	@Override
	public void init(){
		super.init();
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Object action = invocation.getAction();
		if (action instanceof PaginationInterface) {
			Map<String, Object> parameters = invocation.getInvocationContext().getParameters();
			Map<String, Object> pageParams =  PageFactory.getPaginationParams(strategy, queryKey, parameters);
			((PaginationInterface)action).setPageParams(pageParams);
			((PaginationInterface)action).setStrategy(strategy);
		}
		return invocation.invoke();
	}

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		this.intercept(invocation);
		return invocation.invoke();
	}
	
    /**
     * Return a collection from the comma delimited String.
     *
     * @param commaDelim the comma delimited String.
     * @return A collection from the comma delimited String. Returns <tt>null</tt> if the string is empty.
     */
    private Set<String> asCollection(String commaDelim) {
        if (commaDelim == null || commaDelim.trim().length() == 0) {
            return null;
        }
        return TextParseUtil.commaDelimitedStringToSet(commaDelim);
    }
}
