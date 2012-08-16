package com.isoftstone.agiledev.osgi.login.action;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.annotation.RequestModel;
import com.isoftstone.agiledev.osgi.core.web.annotation.Result;
import com.isoftstone.agiledev.osgi.login.domain.Auth;

@com.isoftstone.agiledev.osgi.core.web.annotation.Action(
		packageName="login",
		path = "register",
		results={@Result(name="result",type="json",params={"root","result"})}
	)
public class RegisterAction implements Action{

	private com.isoftstone.agiledev.osgi.core.web.Result result = null;
	
	public String execute(@RequestModel Auth auth){
		if(auth.getLoginName()==null){
			result = new com.isoftstone.agiledev.osgi.core.web.Result(false,"用户名不能为空");
		}else if(auth.getLoginPassword()==null){
			result = new com.isoftstone.agiledev.osgi.core.web.Result(false,"密码不能为空");
		}else if(auth.getLoginName().equals("123") && auth.getLoginPassword().equals("123")){
			result = new com.isoftstone.agiledev.osgi.core.web.Result(true,"注册成功");
		}else{
			result = new com.isoftstone.agiledev.osgi.core.web.Result(false,"未知错误");
		}
		
		return "result";
	}

	public com.isoftstone.agiledev.osgi.core.web.Result getResult() {
		return result;
	}
}
