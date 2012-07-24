package com.isoftstone.agiledev.osgi.db.dao;
/**
 * Mybatis-DAO
 * @author david
 */
import java.util.*;

public class BaseDao implements IBaseDao {
	
	private SessionCreater creater = null;

	public BaseDao() {
		super();
	}
	public BaseDao(SessionCreater creater) {
		super();
		this.creater = creater;
	}

	
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.osgi.db.dao.IBaseDao#list(java.lang.String, java.lang.Object)
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List list(String statement,Object params){
		return creater.openSession().selectList(statement, params);
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.osgi.db.dao.IBaseDao#find(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object find(String statement,Object params){
		return creater.openSession().selectOne(statement,params);
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.osgi.db.dao.IBaseDao#save(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object save(String statement,Object o){
		int result = creater.openSession().insert(statement, o);
		if(result>0)
			return o;
		return null;
	}
	/* (non-Javadoc)
	 * @see com.isoftstone.agiledev.osgi.db.dao.IBaseDao#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object update(String statement,Object o){
		int result = creater.openSession().update(statement, o);
		if(result>0)
			return o;
		return null;
	}
}
