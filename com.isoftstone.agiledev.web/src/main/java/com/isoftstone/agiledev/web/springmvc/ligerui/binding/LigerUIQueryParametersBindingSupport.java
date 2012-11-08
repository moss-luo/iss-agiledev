package com.isoftstone.agiledev.web.springmvc.ligerui.binding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;

import com.isoftstone.agiledev.web.springmvc.binding.BindingSupport;

public class LigerUIQueryParametersBindingSupport implements BindingSupport {
	public static final int DEFAULT_DEFAULT_ROWS = 10;
	public static final int DEFAULT_MAX_ROWS = 50;
	
	protected int defaultRows = DEFAULT_DEFAULT_ROWS;
	protected int maxRows = DEFAULT_MAX_ROWS;
	
	private static final String PAGE_KEY = "page";
	private static final String ROWS_KEY = "pagesize";
	private static final String ORDER_KEY = "sortorder";
	private static final String SORT_KEY = "sortname";

	@Override
	public void adjustProperties(MutablePropertyValues mpvs) {
		String pageKey = getPageKey();
		String rowsKey = getRowsKey();
		String orderKey = getOrderKey();
		String sortKey = getSortKey();
		
		if (pageKey == null || rowsKey == null)
			return;
		
		if (!mpvs.contains(pageKey) || !mpvs.contains(rowsKey)) {
			return;
		}
		
		List<PropertyValue> queryParameters = new ArrayList<PropertyValue>();
		for (PropertyValue pv : mpvs.getPropertyValueList()) {
			if (pageKey.equals(pv.getName())) {
				String parameter = (String)pv.getValue();
				if (parameter == null) {
					queryParameters.add(new PropertyValue("page", 1));
				} else {
					queryParameters.add(new PropertyValue("page", pv.getValue()));
				}
			} else if (rowsKey.equals(pv.getName())) {
				String parameter = (String)pv.getValue();
				if (parameter == null) {
					queryParameters.add(new PropertyValue("rows", DEFAULT_DEFAULT_ROWS));
				} else {
					queryParameters.add(new PropertyValue("rows", pv.getValue()));
				}
			} else if (orderKey.equals(pv.getName())) {
				queryParameters.add(new PropertyValue("order", pv.getValue()));
			} else if (sortKey.equals(pv.getName())) {
				queryParameters.add(new PropertyValue("sort", pv.getValue()));
			} else {
				// ignore other parameters
			}
		}
		
		for (PropertyValue propertyValue : queryParameters) {
			mpvs.addPropertyValue(propertyValue);
		}
	}
	
	protected String getPageKey() {
		return PAGE_KEY;
	}
	
	protected String getRowsKey() {
		return ROWS_KEY;
	}
	
	protected String getOrderKey() {
		return ORDER_KEY;
	}
	
	protected String getSortKey() {
		return SORT_KEY;
	}
	
	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}
	
	public void setDefaultRows(int defaultRows) {
		this.defaultRows = defaultRows;
	}

}
