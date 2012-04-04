package com.isoftstone.agiledev.actions.system.role;

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

	private List<Role> results;

	@Resource(name="roleService")
	private BaseService<Role> roleManager = null;
	@QueryParametersMap
	Map<String,Object> queryCondition = new HashMap<String,Object>();
	public String execute(){
		results = roleManager.list(queryCondition,new Role());
		return "list";
	}
	@Action("list2")
	public String execute2(){
//		queryCondition.clear();
//		queryCondition.put("limit", 1);
//		queryCondition.put("offset", 1000);
		results = roleManager.list(null,new Role());
		return "result";
	}
	
	public List<Role> getResults() {
		return results;
	}

	public void setResults(List<Role> results) {
		this.results = results;
	}


	@Override
	public int getTotal() {
		return roleManager.getTotal();
	}
	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}
	
}
