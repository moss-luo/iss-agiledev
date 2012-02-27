package com.isoftstone.agiledev.pager;

import java.util.Map;

public interface PaginationInterface {

	public Map<String, Object> getPageParams() ;

	public void setPageParams(Map<String, Object> pageParams) ;
	
	public String getStrategy() ;

	public void setStrategy(String strategy) ;

	public PageResult getPageResult(Map<String, Object> rsMap);
}
