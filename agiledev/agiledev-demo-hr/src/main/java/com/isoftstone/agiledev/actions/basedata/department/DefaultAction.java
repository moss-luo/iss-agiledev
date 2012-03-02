package com.isoftstone.agiledev.actions.basedata.department;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.manages.BaseService;

public class DefaultAction {

	private Department department;
	private OperationResult result;
	private String id;
	@Resource(name="baseService")
	private BaseService<Department> departmentManager;
	@Action("create")
	public String create(){
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
}
