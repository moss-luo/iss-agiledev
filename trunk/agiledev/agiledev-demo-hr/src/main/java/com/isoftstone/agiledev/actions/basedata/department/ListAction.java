package com.isoftstone.agiledev.actions.basedata.department;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.isoftstone.agiledev.query.SummaryProvider;
@Results({
	@Result(name="list",type="datagrid-json",params={"root","results"}),
	@Result(name="result",type="json",params={"root","results"})
})
public class ListAction implements SummaryProvider{

	List<Department> results = null;
	@Resource(name="baseService")
	private BaseService<Department> departmentManager = null;
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	public String execute(){
		results = departmentManager.list(queryCondition,new Department());
		return "list";
	}
	@Action("list2")
	public String execute2(){
		queryCondition.clear();
		queryCondition.put("limit", 1);
		queryCondition.put("offset", 1000);
		results=departmentManager.list(queryCondition,new Department());
		return "result";
	}
	@Override
	public int getTotal() {
		return departmentManager.getTotal();
	}

	public List<Department> getResults() {
		return results;
	}
	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}
}
