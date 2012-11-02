package com.isoftstone.agiledev.hrdemo.system.app.internal.user;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.agiledev.core.query.Parameter;
import com.isoftstone.agiledev.core.query.mybatis.ParametersMapBuilder;
import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;



@Transactional
@Service
public class UserManager implements IUserManager {
	@Resource
	private SqlSession sqlSession;
	
	@Resource
	private ParametersMapBuilder parametersMapBuilder;

	@Override
	public void add(User user) {
//		sqlSession.selectOne("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.total", name);
		sqlSession.insert("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.save", user);
	}

	@Override
	public void update(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotal(String name) {
		return (Integer)sqlSession.selectOne("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.total", name);
	}

	@Override
	public void remove(int[] ids) {
		sqlSession.delete("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.batchRemove", ids);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list(String name, int start, int end, String orderBy) {
		return (List<User>)sqlSession.selectList("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.list",
				parametersMapBuilder.build(start, end, orderBy, new Parameter("name", name)));
	}
	

}

