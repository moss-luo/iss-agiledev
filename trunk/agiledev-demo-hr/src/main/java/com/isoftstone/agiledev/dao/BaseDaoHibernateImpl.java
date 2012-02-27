package com.isoftstone.agiledev.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class BaseDaoHibernateImpl extends HibernateDaoSupport implements
		BaseDao {

	@SuppressWarnings("rawtypes")
	@Override
	public List list(Map<String, Object> params, String statement) {
		return null;
	}

	@Override
	public void save(Object model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object model) {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(Object model, String statement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Object model, String statement) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Serializable id, String statement) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object single(Map<String, Object> params, String statement) {
		// TODO Auto-generated method stub
		return null;
	}

}
