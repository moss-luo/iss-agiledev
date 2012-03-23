package com.isoftstone.agiledev.actions.login;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


@Results({
	@Result(name="login", location="/main.html",type="redirect"),
	@Result(name="fail",location="/login.html",type="redirect")
})
public class LoginAction {
    @Resource(name="baseService")
    private BaseService<User> userBaseService=null;
    @QueryParametersMap
    public User user;
	public String execute(){
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("userId", user.getUserId());
		p.put("password", user.getPassword());
		if(!userBaseService.login(p, new User())){
			return "fail";
		}else {
			return "login";
		}
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
}
