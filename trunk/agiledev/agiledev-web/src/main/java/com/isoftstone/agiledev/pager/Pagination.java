package com.isoftstone.agiledev.pager;

import java.util.List;

import com.isoftstone.agiledev.pager.easyui.EasyuiPagerConstant;

public class Pagination {
	
	private String pageNumKey = EasyuiPagerConstant.DEFAULT_PAGE_NUM_KEY;
	
	private String rowsKey = EasyuiPagerConstant.DEFAULT_ROWS_KEY;
	
	private String limitFromKey = EasyuiPagerConstant.DEFAULT_LIMIT_FROM_KEY;
	
	private String limitToKey = EasyuiPagerConstant.DEFAULT_LIMIT_TO_KEY;
	
	private String sortKey = EasyuiPagerConstant.DEFAULT_SORT_KEY;
	
	private String orderKey = EasyuiPagerConstant.DEFAULT_ORDER_KEY;
	
	private int total;
	
	private int page;
	
	private List<Object> rows;
	
	private int pageSize = 10;
	
	private int pageNumber;
	
	@SuppressWarnings("unused")
	private int from;
	
	@SuppressWarnings("unused")
	private int to;
	
	/**
	 * desc
	 * asc
	 */
	private String order;
	
	/**
	 * sort field
	 */
	private String sort;
	
	
//	private transient static ThreadLocal<Pagination> tl = new ThreadLocal<Pagination>();

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Object> getRows() {
		return rows;
	}

	public void setRows(List<Object> rows) {
		this.rows = rows;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
//	public static void setValue(Pagination p){
//		tl.set(p);
//	}
//	
//	public static Pagination getPagination(){
//		return tl.get();
//	}
	
	public int getFrom() {
		return from=page>0?(page-1)*pageSize:page*pageSize;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	public int getTo() {
		return to=page>0?page*pageSize:(page+1)*pageSize;
	}

	public void setTo(int to) {
		this.to = to;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{").append("page:").append(page).append(",rows:").append(rows).append("}");
		return sb.toString();
	}
	
	public String getPageNumKey() {
		return pageNumKey;
	}

	public void setPageNumKey(String pageNumKey) {
		this.pageNumKey = pageNumKey;
	}

	public String getRowsKey() {
		return rowsKey;
	}

	public void setRowsKey(String rowsKey) {
		this.rowsKey = rowsKey;
	}

	public String getLimitFromKey() {
		return limitFromKey;
	}

	public void setLimitFromKey(String limitFromKey) {
		this.limitFromKey = limitFromKey;
	}

	public String getLimitToKey() {
		return limitToKey;
	}

	public void setLimitToKey(String limitToKey) {
		this.limitToKey = limitToKey;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	
}
