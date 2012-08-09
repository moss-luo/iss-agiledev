package com.isoftstone.agiledev.web.springmvc.easyui;

import com.isoftstone.agiledev.web.AbstractQueryParametersReader;

public class EasyUiQueryParametersReader extends AbstractQueryParametersReader {
	private static final String PAGE_KEY = "page";
	private static final String ROWS_KEY = "rows";
	private static final String ORDER_KEY = "order";
	private static final String SORT_KEY = "sort";
	
	@Override
	protected String getPageKey() {
		return PAGE_KEY;
	}
	
	@Override
	protected String getRowsKey() {
		return ROWS_KEY;
	}
	
	@Override
	protected String getOrderKey() {
		return ORDER_KEY;
	}
	
	@Override
	protected String getSortKey() {
		return SORT_KEY;
	}
	
}
