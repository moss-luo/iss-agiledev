package com.isoftstone.agiledev.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("mybatis")
public class BaseDaoMyBatisImpl extends SqlSessionDaoSupport implements BaseDao {

	@SuppressWarnings("rawtypes")
	@Override
	public List list(Map<String, Object> params, String statement) {
		SqlSession session = this.getSqlSession();
//		sessionFactory.getConfiguration().buildAllStatements()
		return session.selectList(statement, params);
	}

	@Override
	public void save(Object model) {
	}

	@Override
	public void update(Object model) {

	}

	@Override
	public void save(Object model, String statement) {
		this.getSqlSession().insert(statement, model);
	}

	@Override
	public void update(Object model, String statement) {
		this.getSqlSession().update(statement, model);
	}

	@Override
	public void remove(Serializable id, String statement) {
		this.getSqlSession().delete(statement, id);
	}

	@Override
	public Object single(Map<String, Object> params, String statement) {
		return this.getSqlSession().selectOne(statement, params);
	}

}
