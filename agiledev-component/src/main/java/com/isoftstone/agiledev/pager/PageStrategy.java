package com.isoftstone.agiledev.pager;

import java.util.Map;
import java.util.Set;

public interface PageStrategy {
	
	Map<String,Object> getPaginationParams(Set<String> queryKey,Map<String,Object> params);
	
	PageResult getPageResult(Map<String, Object> rsMap);
}
