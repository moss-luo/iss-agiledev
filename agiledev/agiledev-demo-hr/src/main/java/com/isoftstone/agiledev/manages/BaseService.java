package com.isoftstone.agiledev.manages;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.isoftstone.agiledev.query.SummaryProvider;

public interface BaseService<T> extends SummaryProvider {

	public abstract List<T> list(Map<String, Object> p, T t);

	public abstract void save(T t);

	public abstract void update(T t);

	public abstract void remove(Serializable id, T t);

	public abstract String getOperationCommand(T t);
	
	public abstract boolean unique(Map<String,Object> p,T t);
	

}