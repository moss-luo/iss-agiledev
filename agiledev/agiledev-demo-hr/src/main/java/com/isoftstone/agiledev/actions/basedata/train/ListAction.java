package com.isoftstone.agiledev.actions.basedata.train;

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

	List<Train> results=null;
	@Resource(name="baseService")
	private BaseService<Train> trainManager=null;
	@QueryParametersMap
	private Map<String, Object> queryCondition=new HashMap<String, Object>();
	public String execute(){
		results=trainManager.list(queryCondition, new Train());
		return "list";
	}
	
	@Action("list2")
	public String execute2()
	{
		queryCondition.clear();
		queryCondition.put("limit", 1);
		queryCondition.put("offset", 1000);
		results=trainManager.list(queryCondition, new Train());
		return "result";
	}
	
	@Override
	public int getTotal() {
		return trainManager.getTotal();
	}

	public List<Train> getResults(){
		return results;
	}
}
