package com.isoftstone.agiledev.dao;

import java.util.List;

public interface IBaseDao {

	public abstract void setSessionCreater(SessionCreater creater);
	void registerDomain(String alias,Class<?> domainClass);
	void registerMapper(Class<?> mapperClass);
	/**
	 * 查询list，
	 * @param statement 
	 * @param params 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public abstract List list(String statement, Object params);

	/**
	 * 查询单个对象
	 * @param statement
	 * @param params
	 * @return
	 */
	public abstract Object find(String statement, Object params);

	/**
	 * 保存对象
	 * @param statement
	 * @param o
	 * @return
	 */
	public abstract Object save(String statement, Object o);

	/**
	 * 更新对象
	 * @param statement
	 * @param o
	 * @return
	 */
	public abstract Object update(String statement, Object o);

}