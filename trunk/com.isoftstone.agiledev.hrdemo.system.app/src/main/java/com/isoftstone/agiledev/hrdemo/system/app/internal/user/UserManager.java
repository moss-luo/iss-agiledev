package com.isoftstone.agiledev.hrdemo.system.app.internal.user;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isoftstone.agiledev.hrdemo.system.app.user.IUserManager;
import com.isoftstone.agiledev.hrdemo.system.app.user.User;

@Transactional
@Service
public class UserManager implements IUserManager {
	@Resource
	private SqlSession sqlSession;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list(Map<String, Object> queryParameters) {
		return (List<User>)sqlSession.selectList("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.list", queryParameters);	
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub
		
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
	public int getTotal() {
		return (Integer)sqlSession.selectOne("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.total");
	}

	@Override
	public void remove(int[] ids) {
		sqlSession.delete("com.isoftstone.agiledev.hrdemo.system.app.user.UserMapper.batchRemove", ids);
	}
	

}
