package com.isoftstone.agiledev.actions.basedata.level;

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

	List<Level> results = null;
	@Resource(name="baseService")
	private BaseService<Level> levelManager = null;
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	public String execute(){
		results = levelManager.list(queryCondition,new Level());
		return "result";
	}
	@Override
	public int getTotal() {
		return levelManager.getTotal();
	}

	public List<Level> getResults() {
		return results;
	}

}
