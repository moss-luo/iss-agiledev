package com.isoftstone.agiledev.actions.basedata.employee;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.manages.BaseService;

public class DefaultAction {

	private Employee employee;
	private OperationResult result;
	private String id;
	@Resource(name="baseService")
	private BaseService<Employee> departmentManager;
	@Action("create")
	public String create(){
		departmentManager.save(employee);
		result = new OperationResult(true,"新建员工成功");
		return "result";
	}
	@Action("update")
	public String update(){
		departmentManager.update(employee);
		result = new OperationResult(true, "修改员工成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		departmentManager.remove(id,new Employee());
		result = new OperationResult(true, "删除员工成功");
		return "result";
	}
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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
