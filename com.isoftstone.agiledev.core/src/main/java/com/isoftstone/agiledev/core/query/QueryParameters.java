package com.isoftstone.agiledev.core.query;

import com.isoftstone.agiledev.core.pager.Pageable;

public class QueryParameters implements Pageable, Sortable {
	private String order;
	private String sort;
	private int page;
	private int rows;
	
	public static final int UNKOWN_PAGE = -1;
	public static final int UNKOWN_ROWS = -1;

	@Override
	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String getOrder() {
		return order;
	}

	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String getSort() {
		return sort;
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public void setRows(int rows) {
		this.rows = rows;
	}

	@Override
	public int getPage() {
		return page;
	}

	@Override
	public int getRows() {
		return rows;
	}

}
