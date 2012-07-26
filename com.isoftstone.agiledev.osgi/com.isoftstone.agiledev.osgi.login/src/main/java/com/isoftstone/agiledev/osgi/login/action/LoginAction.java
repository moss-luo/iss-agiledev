package com.isoftstone.agiledev.osgi.login.action;

import javax.annotation.Resource;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.ActionContext;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestParameter;
import com.isoftstone.agiledev.osgi.core.web.annotation.Result;
import com.isoftstone.agiledev.osgi.login.domain.Auth;
import com.isoftstone.agiledev.osgi.login.service.LoginService;

@com.isoftstone.agiledev.osgi.core.web.annotation.Action(
			packageName="login",
			path="login",
			results={@Result(name="result",type="json",params={"root","result"})})
public class LoginAction implements Action{
	
	private com.isoftstone.agiledev.osgi.core.web.Result result = null;
	
	@Resource
	private LoginService loginService = null;
	public String execute(@RequestParameter("loginName")String loginName,
						@RequestParameter("loginPwd")String loginPwd,
						@RequestParameter("validateCode")String validateCode){
		
		String temp = (String) ActionContext.getSession().getAttribute("random");
		
		if(!validateCode.equalsIgnoreCase(temp)){
			result = new com.isoftstone.agiledev.osgi.core.web.Result(false, "验证码错误!");
			return "result";
		}
		
		Auth auth = loginService.login(loginName, loginPwd);
		if(auth!=null)
			result = new com.isoftstone.agiledev.osgi.core.web.Result(true, "登录成功");
		else
			result = new com.isoftstone.agiledev.osgi.core.web.Result(false, "用户名/密码错误");
		return "result";
	}
	
	
	public com.isoftstone.agiledev.osgi.core.web.Result getResult() {
		return result;
	}
}