package com.isoftstone.agiledev.osgi.login.action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpSession;

import com.isoftstone.agiledev.osgi.core.web.ActionContext;
import com.isoftstone.agiledev.osgi.core.web.annotation.Action;
import com.isoftstone.agiledev.osgi.core.web.annotation.Result;
import com.isoftstone.agiledev.osgi.core.web.annotation.Results;
import com.isoftstone.agiledev.osgi.login.service.RandomNumUtil;

@Action(packageName="login",path = "rand")
@Results(
	@Result(name="rand",type="stream",params={"inputStream","inputStream","contentType","image/jpeg"})
)
public class RandomAction implements com.isoftstone.agiledev.osgi.core.web.Action{
	private ByteArrayInputStream inputStream;
	
    public String execute() throws Exception{  
    	
        RandomNumUtil rdnu=RandomNumUtil.Instance(); 
        
       this.setInputStream(rdnu.getImage());//取得带有随机字符串的图片 
       
        HttpSession session=ActionContext.getSession();
        session.setAttribute("random", rdnu.getString());
        System.out.println("保存在session中的--"+rdnu.getString());
        return "rand";  
    }  
    public void setInputStream(ByteArrayInputStream inputStream) {  
        this.inputStream = inputStream;  
    }  
   public ByteArrayInputStream getInputStream() {  
        return inputStream;  
    } 
}
