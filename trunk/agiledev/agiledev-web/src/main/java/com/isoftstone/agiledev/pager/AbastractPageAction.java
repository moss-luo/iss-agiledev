package com.isoftstone.agiledev.pager;

import java.util.Map;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class AbastractPageAction extends ActionSupport implements PaginationInterface {
	
	private String strategy;
	
	private Map<String,Object> pageParams;

	public Map<String, Object> getPageParams() {
		return pageParams;
	}

	public void setPageParams(Map<String, Object> pageParams) {
		this.pageParams = pageParams;
	}
	
	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public PageResult getPageResult(Map<String, Object> rsMap){
		return PageFactory.getPageResult(strategy, rsMap);
	}

}
