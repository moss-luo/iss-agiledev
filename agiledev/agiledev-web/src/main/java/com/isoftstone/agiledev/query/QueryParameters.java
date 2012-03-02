package com.isoftstone.agiledev.query;

import com.isoftstone.agiledev.pager.Pageable;

public class QueryParameters implements Pageable, Sortable {
	private String order;
	private String sort;
	private int page;
	private int rows;

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
