package com.isoftstone.agiledev.actions.basedata.program;


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
	
	private List<Program> results=null;
	
	@Resource(name="baseService")
	private BaseService<Program> programsManager=null;
	
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	
	public String execute()
	{
		results=programsManager.list(queryCondition, new Program());
		
		return "result";
	}
	
	@Override
	public int getTotal() 
	{
		return programsManager.getTotal();
	}

	public List<Program> getResults() {
		return results;
	}

	public void setResults(List<Program> results) {
		this.results = results;
	}

	public BaseService<Program> getProgramsManager() {
		return programsManager;
	}

	public void setProgramsManager(BaseService<Program> programsManager) {
		this.programsManager = programsManager;
	}
}
