package com.isoftstone.agiledev.actions.login;

import java.io.ByteArrayInputStream;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.isoftstone.agiledev.manages.RandomNumUtil;
import com.opensymphony.xwork2.ActionContext;

@Result(name="rand",type="stream",params={"inputStream","rand","contentType","image/jpeg"})
@Namespace("/authcode")
public class RandomAction{
	private ByteArrayInputStream inputStream;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Action("rand")
    public String execute() throws Exception{  
    	
        RandomNumUtil rdnu=RandomNumUtil.Instance(); 
        
       this.setInputStream(rdnu.getImage());//取得带有随机字符串的图片 
       
        //ActionContext.getContext().getSession().put("random",rdnu.getString());//取得随机字符串放入HttpSession  
        ActionContext actionContext=ActionContext.getContext();
        Map session=actionContext.getSession();
        session.put("random", rdnu.getString());
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
