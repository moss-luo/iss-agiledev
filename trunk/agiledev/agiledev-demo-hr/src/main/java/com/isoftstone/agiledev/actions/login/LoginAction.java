package com.isoftstone.agiledev.actions.login;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import sun.tools.tree.ThisExpression;

import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.opensymphony.xwork2.ActionContext;
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
    
    private String userId;
    
    private String validateCode;
	
	public String execute(){
		String str=(String)(ActionContext.getContext().getSession().get("random"));//取得session保存中的字符串
		//System.out.println("在session中拿出的--"+str);
		if(!str.equalsIgnoreCase(this.getValidateCode())){;
			return "fail";
		}
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
	
	public String getValidateCode() {
		return validateCode;
	}
	
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
}
