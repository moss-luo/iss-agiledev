package com.isoftstone.agiledev.query;

public abstract class AbstractQueryManager implements QueryManager {
	private int page;
	private int rows;
	private String order;
	private String sort;
	
	public AbstractQueryManager() {
		page = NON_PAGEABLE;
	}

	@Override
	public void setOrder(String order) {
		this.order = order;
	}
	
	@Override
	public void setSort(String sort) {
		this.sort = sort;
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
	
	@Override
	public String getOrder() {
		return order;
	}
	
	@Override
	public String getSort() {
		return sort;
	}
	
	public <T> T query(QueryExecutor<T> executor) {
		return QueryUtils.query(this, executor);
	}
}
