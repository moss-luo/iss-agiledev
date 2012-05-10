package com.isoftstone.agiledev.actions.system.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;

import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.isoftstone.agiledev.query.SummaryProvider;

@Result(name="result",type="ligerui-datagrid-json",params={"root","results"})
public class LigeruiListAction implements SummaryProvider{

	private List<User> results = null;
	@Resource(name="baseService")
	private BaseService<User> userService = null;
	@QueryParametersMap
	private Map<String,Object> queryCondition = new HashMap<String,Object>();
	
	public String execute(){
		results = userService.list(queryCondition, new User());
		return "result";
	}

	public Map<String, Object> getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(Map<String, Object> queryCondition) {
		this.queryCondition = queryCondition;
	}

	public List<User> getResults() {
		return results;
	}

	@Override
	public int getTotal() {
		return userService.getTotal();
	}
}
