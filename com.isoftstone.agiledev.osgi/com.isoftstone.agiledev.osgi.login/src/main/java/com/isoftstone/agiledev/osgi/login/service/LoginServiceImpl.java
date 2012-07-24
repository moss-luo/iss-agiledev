package com.isoftstone.agiledev.osgi.login.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.isoftstone.agiledev.osgi.core.service.Service;
import com.isoftstone.agiledev.osgi.db.dao.IBaseDao;
import com.isoftstone.agiledev.osgi.login.domain.Auth;

@Service
public class LoginServiceImpl implements LoginService {

	@Resource
	private IBaseDao dao = null;
	@Override
	public Auth login(String loginName,String loginPassword){
		Map<String, String> p = new HashMap<String, String>();
		p.put("loginName", loginName);
		p.put("loginPassword", loginPassword);
		return (Auth) dao.find("com.isoftstone.agiledev.osgi.login.domain.AuthMapper.login", p);
	}
}
