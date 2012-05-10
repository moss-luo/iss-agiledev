package com.isoftstone.agiledev.actions.login;


import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.actions.system.user.User;
import com.isoftstone.agiledev.manages.system.user.UserService;
import com.isoftstone.agiledev.query.QueryParametersMap;
import com.opensymphony.xwork2.ActionContext;

@Results({
//	@Result(name="easyui", location="/main.html",type="redirect")
//	@Result(name="ligerui", location="/main_ligerui.html",type="redirect"),
//	@Result(name="error",location="/login.html",type="redirect"),
//	@Result(name="input",location="/login.html",type="redirect")
	@Result(name="result",type="json",params={"root","result"})
})
public class LoginAction {
    @Resource(name="userService")
    private UserService userService=null;
    private OperationResult result = null;
    private String version;
    @QueryParametersMap
    public User user;
    
    private String validateCode;
	
	public String execute(){
		String str=(String)(ActionContext.getContext().getSession().get("random"));//取得session保存中的字符串
		ServletActionContext.getRequest().getSession().setAttribute("version", version);
		if(!str.equalsIgnoreCase(this.getValidateCode())){
			result = new OperationResult(false, "验证码错误");
//			return "input";
		}
		user = userService.login(user);
		if(user==null){
			result = new OperationResult(false, "用户名或密码错误");
//			return "error";
		}else {
			ServletActionContext.getRequest().getSession().setAttribute("login_user", user);
			result = new OperationResult(true);
//			return null!=version?version:"easyui";
		}
		return "result";
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
	public OperationResult getResult() {
		return result;
	}
	public void setResult(OperationResult result) {
		this.result = result;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
}