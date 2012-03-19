package com.isoftstone.agiledev.actions.basedata.department;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.manages.BaseService;
import com.opensymphony.xwork2.ModelDriven;
@Results({
	@Result(name="result",type="json",params={"root","result","contentType","text/html"}),
	@Result(name="init",type="initjson")
})
public class DefaultAction implements ModelDriven<Department>{

	private Department department=null;
	private OperationResult result=null;
	private String id=null;
	@Resource(name="baseService")
	private BaseService<Department> departmentManager=null;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("create")
	public String create(){
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("depName", department.getDepName());
		if(!departmentManager.unique(p, new Department())){
			result=new OperationResult(false,"该部门已存在");
			return "result";
		}
		departmentManager.save(department);
		result = new OperationResult(true,"创建部门成功");
		return "result";
	}
	@Action("update")
	public String update(){
		departmentManager.update(department);
		result = new OperationResult(true, "修改部门成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		departmentManager.remove(id,new Department());
		result = new OperationResult(true, "删除部门成功");
		return "result";
	}
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
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
	public Department getModel() {
		if(department==null){
			department=new Department();
		}
		return department;
	}
	
}
