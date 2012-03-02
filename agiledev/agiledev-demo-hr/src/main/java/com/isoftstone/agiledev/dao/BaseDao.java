package com.isoftstone.agiledev.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao {

	@SuppressWarnings("rawtypes")
	List list(Map<String,Object> params,String statement);
	Object single(Map<String,Object> params,String statement);
	void save(Object model);
	void update(Object model);
	void save(Object model,String statement);
	void update(Object model,String statement);
	void remove(Serializable id,String statement);
}
