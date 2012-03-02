package com.isoftstone.agiledev.pager;

import com.opensymphony.xwork2.Action;

public class PaginationAction implements Action{
	
	private Pagination pagination;

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	@Override
	public String execute() throws Exception {
		return null;
	}
}
