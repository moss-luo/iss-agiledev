package com.isoftstone.agiledev.actions.login;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.ActionContext;
@Results({
	@Result(name="success",location="/login.html"),
	@Result(name="input",location="/login.html")
})
public class CheckAction {
	private String str;
	private String message;
	public String execute(){  
        String str2=(String)(ActionContext.getContext().getSession().get("random"));//取得session保存中的字符串  
        //下面就是将session中保存验证码字符串与客户输入的验证码字符串对比了  
        if(str2.equals(this.getStr())){  
         message = "验证码正确";
          return "success";  
        }else{  
         message = "验证码错误";
         return "input";  
        }  
    }  
	
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		this.str = str;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
