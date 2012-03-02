package com.isoftstone.agiledev.pager.easyui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.isoftstone.agiledev.pager.PageResult;
import com.isoftstone.agiledev.pager.PageStrategy;

public class EasyuiPaginationStrategy implements PageStrategy {
	
	private String pageNumKey = EasyuiPagerConstant.DEFAULT_PAGE_NUM_KEY;
	
	private String rowsKey = EasyuiPagerConstant.DEFAULT_ROWS_KEY;
	
	private String limitFromKey = EasyuiPagerConstant.DEFAULT_LIMIT_FROM_KEY;
	
	private String limitToKey = EasyuiPagerConstant.DEFAULT_LIMIT_TO_KEY;
	
	private String sortKey = EasyuiPagerConstant.DEFAULT_SORT_KEY;
	
	private String orderKey = EasyuiPagerConstant.DEFAULT_ORDER_KEY;	
	
	public EasyuiPaginationStrategy(){
		
	}

	@Override
	public Map<String, Object> getPaginationParams(Set<String> queryKey,Map<String, Object> params) {
		Map<String, Object> p = new HashMap<String , Object>();
		if( params.containsKey(this.pageNumKey) && params.containsKey(this.rowsKey)){
			String pageNumStr = ((String[]) params.get(this.pageNumKey))[0];
			String rowsNumStr = ((String[]) params.get(this.rowsKey))[0];
			if( null!=pageNumStr && null!=rowsNumStr){
				try {
					int page = Integer.parseInt(pageNumStr);
					int rows = Integer.parseInt(rowsNumStr);
					p.put(this.limitFromKey, page==0?0:(page-1)*rows);
					p.put(this.limitToKey, page==0?rows:page*rows);
				} catch (NumberFormatException e) {
					p.put(this.limitFromKey, 0);
					p.put(this.limitToKey, EasyuiPagerConstant.DEFAULT_PAGE_ROW);					
				}
			}		
		}
		
		if(params.containsKey(this.sortKey)){
			p.put(this.sortKey, ((String[]) params.get(this.sortKey))[0]);
		}
		
		if(params.containsKey(this.orderKey)){
			p.put(this.orderKey, ((String[]) params.get(this.orderKey))[0]);
		}
		if(null!=queryKey){
			for(String query:queryKey){
				if(params.containsKey(query)){
					p.put(query, ((String[]) params.get(query))[0]);
				}
			}
		}
		return p;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageResult getPageResult(Map<String, Object> rsMap) {
		EasyPageResult rs = new EasyPageResult();
		Integer total = (Integer) rsMap.get(EasyPageResult.TOTAL_KEY);
		rs.setTotal(total!=null?total:0);
		rs.setRows((List<Object>) rsMap.get(EasyPageResult.RESULT_ROWS_KEY));
		return rs;
	}

}
