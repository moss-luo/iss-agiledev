package com.isoftstone.agiledev.actions.basedata.employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;

import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.isoftstone.agiledev.query.SummaryProvider;

@Result(name="result",type="datagrid-json",params={"root","results"})

public class ListAction implements SummaryProvider{

	private List<Employee> results = null;
	@Resource(name="baseService")
	private BaseService<Employee> employeeManager = null;
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	public String execute(){
		results = employeeManager.list(this.getQueryCondition(),new Employee());
		return "result";
	}
	@Override
	public int getTotal() {
		return employeeManager.getTotal();
	}

	public List<Employee> getResults() {
		return results;
	}
	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}
	
	
	

}
