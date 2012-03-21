package com.isoftstone.agiledev.actions.system.user;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.codec.binary.Base64;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.initform.Initialization;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.validater.Validation;
import com.isoftstone.agiledev.validater.Validations;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;

@Results({
	@Result(name = "result", type = "json", params = {"root", "result", "contentType", "text/html"}),
	@Result(name = "init", type = "initjson")
})
public class DefaultAction implements ModelDriven<User>{

	@Resource(name="baseService")
	private BaseService<User> manage;
	
	private OperationResult result = null;
//	@Validation
	@Initialization
	@Validations({
		@Validation(fieldName="userId",validType=StringLengthFieldValidator.class,params={"message","用户名必须在1-10位之间","minLength","1","maxLength","10"})
	})
	private User user = null;
	private String id;
	private String name=null;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("create")
	public String create(){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", user.getUserId());
		if(!manage.unique(p, new User())){
			result = new OperationResult(false,"该用户已存在");
			return "result";
		}
		
		if(user.getPhoto()!=null && !"".equals(user.getPhoto()))
			user.setPhoto(new String(Base64.decodeBase64(user.getPhoto())));
		manage.save(user);
		result = new OperationResult(true, "添加用户成功");
		return "result";
	}
	@Action("update")
	public String update(){
		if(user.getPhoto()!=null && !"".equals(user.getPhoto()))
			user.setPhoto(new String(Base64.decodeBase64(user.getPhoto())));
		manage.update(user);
		result = new OperationResult(true, "修改用户成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		manage.remove(id,new User());
		result = new OperationResult(true, "删除用户成功");
		return "result";
	}
//	@Action("unique")
//	public String unique(){
//		Map<String, Object> p = new HashMap<String, Object>();
//		p.put("userId", name);
//		if(manage.unique(p, new User()))
//			result = new OperationResult(false);
//		else
//			result = new OperationResult(true);
//		return "result";
//	}
//	

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public OperationResult getResult() {
		return result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public User getModel() {
		if(user == null)
			user = new User();
		return user;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
