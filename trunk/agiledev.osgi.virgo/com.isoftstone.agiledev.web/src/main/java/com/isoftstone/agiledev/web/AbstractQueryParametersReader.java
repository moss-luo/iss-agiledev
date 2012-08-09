package com.isoftstone.agiledev.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.isoftstone.agiledev.core.query.QueryParameters;
import com.isoftstone.agiledev.core.query.QueryParametersReader;

public abstract class AbstractQueryParametersReader  implements QueryParametersReader {
	public static final int DEFAULT_DEFAULT_ROWS = 20;
	public static final int DEFAULT_MAX_ROWS = 50;
	
	protected int defaultRows = DEFAULT_DEFAULT_ROWS;
	protected int maxRows = DEFAULT_MAX_ROWS;
	
	@Override
	public QueryParameters readQueryParameters(Map<String, Object> parameters) {
		String pageKey = getPageKey();
		String rowsKey = getRowsKey();
		String orderKey = getOrderKey();
		String sortKey = getSortKey();
		
		if (pageKey == null || rowsKey == null)
			return null;
		
		if (!parameters.containsKey(pageKey) || !parameters.containsKey(rowsKey)) {
			return null;
		}
		
		QueryParameters pp = new QueryParameters();
		List<String> removeLater = new ArrayList<String>();
		for (String key : parameters.keySet()) {
			boolean removeParameter = true;
			if (pageKey.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter == null || parameter.length == 0) {
					pp.setPage(getPageParameter("-1"));
				} else {
					pp.setPage(getPageParameter(parameter[0]));
				}
				
			} else if (rowsKey.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter == null || parameter.length == 0) {
					pp.setRows(getRowsParameter("-1"));
				} else {
					pp.setRows(getRowsParameter(parameter[0]));
				}
			} else if (orderKey != null && orderKey.equals(key)) {
				String[] parameter = (String[])parameters.get(key);
				if (parameter != null && parameter.length != 0) {
					pp.setOrder(parameter[0]);
				}
			} else if (sortKey != null && sortKey.equals(key)) {
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
	
	abstract protected String getPageKey();
	abstract protected String getRowsKey();
	abstract protected String getOrderKey();
	abstract protected String getSortKey();
}