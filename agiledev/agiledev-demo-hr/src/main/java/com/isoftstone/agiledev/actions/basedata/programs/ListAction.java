package com.isoftstone.agiledev.actions.basedata.programs;


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
	
	private List<Programs> results=null;
	
	@Resource(name="baseService")
	private BaseService<Programs> programsManager=null;
	
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	
	public String execute()
	{
		results=programsManager.list(queryCondition, new Programs());
		
		return "result";
	}
	
	@Override
	public int getTotal() 
	{
		return programsManager.getTotal();
	}

	public List<Programs> getResults() {
		return results;
	}

	public void setResults(List<Programs> results) {
		this.results = results;
	}

	public BaseService<Programs> getProgramsManager() {
		return programsManager;
	}

	public void setProgramsManager(BaseService<Programs> programsManager) {
		this.programsManager = programsManager;
	}
}
