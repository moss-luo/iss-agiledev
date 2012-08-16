package com.isoftstone.agiledev.osgi.login.service;

import com.isoftstone.agiledev.osgi.login.domain.Auth;

public interface LoginService {

	public abstract Auth login(String loginName, String loginPassword);

}