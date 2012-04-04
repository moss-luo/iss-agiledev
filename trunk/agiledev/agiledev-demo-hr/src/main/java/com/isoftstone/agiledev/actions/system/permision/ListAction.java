package com.isoftstone.agiledev.actions.system.permision;

import java.util.ArrayList;
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
	@Result(name="result",type="json",params={"root","results"}),
	@Result(name="json2",type="datagrid-json",params={"root","results"})
})
public class ListAction implements SummaryProvider{

	private List<Permision> results = new ArrayList<Permision>();
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	@Resource(name="baseService")
	private BaseService<Permision> permisionManager=null;
	
	public String execute(){
		results= permisionManager.list(null,new Permision());
		return "result";
	}
	@Action("list2")
	public String list(){
		results= permisionManager.list(queryCondition,new Permision());
		return "json2";
	}
	public List<Permision> getResults() {
		return results;
	}
	public void setResults(List<Permision> results) {
		this.results = results;
	}
	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}
	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}
	@Override
	public int getTotal() {
		return permisionManager.getTotal();
	}
	
	
}
