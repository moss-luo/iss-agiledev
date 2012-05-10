package com.isoftstone.agiledev.pager.ligerui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.isoftstone.agiledev.pager.PageResult;
import com.isoftstone.agiledev.pager.PageStrategy;
import com.isoftstone.agiledev.pager.easyui.EasyPageResult;
import com.isoftstone.agiledev.pager.easyui.EasyuiPagerConstant;

public class LigeruiPaginationStrategy implements PageStrategy {

	@Override
	public Map<String, Object> getPaginationParams(Set<String> queryKey,
			Map<String, Object> params) {
		Map<String, Object> p = new HashMap<String , Object>();
		if( params.containsKey(LigeruiPagerConstant.DEFAULT_PAGE_NUM_KEY) && params.containsKey(LigeruiPagerConstant.DEFAULT_ROWS_KEY)){
			String pageNumStr = ((String[]) params.get(LigeruiPagerConstant.DEFAULT_PAGE_NUM_KEY))[0];
			String rowsNumStr = ((String[]) params.get(LigeruiPagerConstant.DEFAULT_ROWS_KEY))[0];
			if( null!=pageNumStr && null!=rowsNumStr){
				try {
					int page = Integer.parseInt(pageNumStr);
					int rows = Integer.parseInt(rowsNumStr);
					p.put(LigeruiPagerConstant.DEFAULT_LIMIT_FROM_KEY, page==0?0:(page-1)*rows);
					p.put(LigeruiPagerConstant.DEFAULT_LIMIT_TO_KEY, page==0?rows:page*rows);
				} catch (NumberFormatException e) {
					p.put(LigeruiPagerConstant.DEFAULT_LIMIT_FROM_KEY, 0);
					p.put(LigeruiPagerConstant.DEFAULT_LIMIT_TO_KEY, EasyuiPagerConstant.DEFAULT_PAGE_ROW);					
				}
			}		
		}
		
		if(params.containsKey(LigeruiPagerConstant.DEFAULT_SORT_KEY)){
			p.put(LigeruiPagerConstant.DEFAULT_SORT_KEY, ((String[]) params.get(LigeruiPagerConstant.DEFAULT_SORT_KEY))[0]);
		}
		
		if(params.containsKey(LigeruiPagerConstant.DEFAULT_ORDER_KEY)){
			p.put(LigeruiPagerConstant.DEFAULT_ORDER_KEY, ((String[]) params.get(LigeruiPagerConstant.DEFAULT_ORDER_KEY))[0]);
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
