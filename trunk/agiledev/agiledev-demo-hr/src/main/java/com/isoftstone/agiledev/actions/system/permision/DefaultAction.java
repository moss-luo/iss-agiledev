package com.isoftstone.agiledev.actions.system.permision;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.initform.Initialization;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.validater.Validation;
import com.opensymphony.xwork2.ModelDriven;

@Results({
	@Result(name = "result", type = "json", params = {"root", "result", "contentType", "text/html"})
})
public class DefaultAction implements ModelDriven<Permision>{

	@Resource(name="baseService")
	private BaseService<Permision> manage;
	
	private OperationResult result = null;
	@Validation
	@Initialization
	private Permision permision = null;
	private String id;
	private String name=null;
	@Action("create")
	public String create(){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("permisionName", permision.getPermisionName());
		if(!manage.unique(p, new Permision())){
			result = new OperationResult(false,"该模块已存在");
			return "result";
		}
		
		if(permision.getPid()!=null){
			permision.setHasChild(1);
			permision.setPname(permision.getPid().split(";")[1]);
			permision.setPid(permision.getPid().split(";")[0]);
		}
		
		manage.save(permision);
		result = new OperationResult(true, "添加模块成功");
		return "result";
	}
	@Action("update")
	public String update(){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("permisionName", permision.getPermisionName());
		if(!manage.unique(p, new Permision())){
			result = new OperationResult(false,"该模块已存在");
			return "result";
		}

		if(permision.getPid()!=null){
			permision.setHasChild(1);
			permision.setPname(permision.getPid().split(";")[1]);
			permision.setPid(permision.getPid().split(";")[0]);
		}
		manage.update(permision);
		result = new OperationResult(true, "修改模块成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		manage.remove(id,new Permision());
		result = new OperationResult(true, "删除模块成功");
		return "result";
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public Permision getModel() {
		if(permision==null)permision = new Permision();
		return permision;
	}
	
}
