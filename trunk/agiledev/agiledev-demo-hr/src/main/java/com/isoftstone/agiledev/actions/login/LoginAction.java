package com.isoftstone.agiledev.actions.login;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name="login", location="/main.html",type="redirect"),
	@Result(name="error",location="/login.html",type="redirect"),
	@Result(name="input",location="/login.html",type="redirect")
})
public class LoginAction {
    @Resource(name="baseService")
    private BaseService<User> userBaseService=null;
    
    @QueryParametersMap
    public User user;
    
    private String validateCode;
	
	public String execute(){
		String str=(String)(ActionContext.getContext().getSession().get("random"));//取得session保存中的字符串
		System.out.println("在session中拿出的--"+str);
		System.out.println("用户输入的验证码--"+validateCode);
		if(!str.equalsIgnoreCase(this.getValidateCode())){
			return "input";
		}
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("userId", user.getUserId());
		p.put("password", user.getPassword());
		if(!userBaseService.login(p, new User())){
			return "error";
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
	
	public String getValidateCode() {
		return validateCode;
	}
	
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
}