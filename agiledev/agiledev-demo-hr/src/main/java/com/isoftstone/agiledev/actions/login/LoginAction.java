package com.isoftstone.agiledev.actions.login;


import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.system.user.UserService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.opensymphony.xwork2.ActionContext;

@Results({
	@Result(name="login", location="/main.html",type="redirect"),
	@Result(name="error",location="/login.html",type="redirect"),
	@Result(name="input",location="/login.html",type="redirect")
})
public class LoginAction {
    @Resource(name="userService")
    private UserService userService=null;
    
    @QueryParametersMap
    public User user;
    
    private String validateCode;
	
	public String execute(){
		String str=(String)(ActionContext.getContext().getSession().get("random"));//取得session保存中的字符串
		if(!str.equalsIgnoreCase(this.getValidateCode())){
			return "input";
		}
		user = userService.login(user);
		if(user==null){
			return "error";
		}else {
			ServletActionContext.getRequest().getSession().setAttribute("login_user", user);
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