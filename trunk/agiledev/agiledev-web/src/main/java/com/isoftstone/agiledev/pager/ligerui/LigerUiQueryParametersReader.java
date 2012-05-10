package com.isoftstone.agiledev.pager.ligerui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.isoftstone.agiledev.query.QueryParameters;
import com.isoftstone.agiledev.query.QueryParametersReader;

public class LigerUiQueryParametersReader implements QueryParametersReader {
	private static final String PAGE_KEY = "page";
	private static final String ROWS_KEY = "pagesize";
	private static final String ORDER_KEY = "order";
	private static final String SORT_KEY = "sort";
	
	private static final int DEFAULT_DEFAULT_ROWS = 20;
	private static final int DEFAULT_MAX_ROWS = 50;
	
	private int defaultRows = DEFAULT_DEFAULT_ROWS;
	private int maxRows = DEFAULT_MAX_ROWS;
	
	@Override
	public QueryParameters readQueryParameters(Map<String, Object> parameters) {
		if (!parameters.containsKey(PAGE_KEY) || !parameters.containsKey(ROWS_KEY)) {
			return null;
		}
		
		QueryParameters pp = new QueryParameters();
		List<String> removeLater = new ArrayList<String>();
		for (String key : parameters.keySet()) {
			boolean removeParameter = true;
			if (PAGE_KEY.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter == null || parameter.length == 0) {
					pp.setPage(getPageParameter("-1"));
				} else {
					pp.setPage(getPageParameter(parameter[0]));
				}
				
			} else if (ROWS_KEY.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter == null || parameter.length == 0) {
					pp.setRows(getRowsParameter("-1"));
				} else {
					pp.setRows(getRowsParameter(parameter[0]));
				}
			} else if (ORDER_KEY.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter != null && parameter.length != 0) {
					pp.setOrder(parameter[0]);
				}
			} else if (SORT_KEY.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter != null && parameter.length != 0) {
					pp.setSort(parameter[0]);
				}
			} else {
				// ignore other parameters
				removeParameter = false;
			}
			
			if (removeParameter) {				
				removeLater.add(key);
			}
		}
		
		for (String key : removeLater) {
			parameters.remove(key);
		}
		
		return pp;
	}
	
	private int getPageParameter(String page) {
		try {
			return Integer.parseInt(page);
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	private int getRowsParameter(String rows) {
		int iRows = -1;
		try {
			iRows = Integer.parseInt(rows);
		} catch (NumberFormatException e) {
			return defaultRows;
		}
		
		if (iRows < 0 || iRows > maxRows) {
			return defaultRows;
		}
		
		return iRows;
	}

	@Override
	public void init(Properties properties) {
		if (properties != null) {
			String sDefaultRows = (String)properties.get("default-rows");
			if (sDefaultRows != null) {
				try {
					defaultRows = Integer.parseInt(sDefaultRows);
				} catch (Exception e) {}
				
			}
			
			String sMaxRows = (String)properties.get("max-rows");
			if (sMaxRows != null) {
				try {
					maxRows = Integer.parseInt(sMaxRows);
				} catch (Exception e) {}
			}
			
			if (maxRows < defaultRows) {
				defaultRows = DEFAULT_DEFAULT_ROWS;
				maxRows = DEFAULT_MAX_ROWS;
			}
		}else{
			defaultRows = 20;
			maxRows = 50;
		}
	}

}
