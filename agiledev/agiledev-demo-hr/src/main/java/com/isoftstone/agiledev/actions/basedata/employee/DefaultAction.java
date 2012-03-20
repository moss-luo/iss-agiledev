package com.isoftstone.agiledev.actions.basedata.employee;

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
	@Result(name = "result", type = "json", params = {"root", "result", "contentType", "text/html"}),
	@Result(name = "init", type = "initjson")
})
public class DefaultAction implements ModelDriven<Employee>{

	@Validation
	@Initialization
	private Employee employee;
	private OperationResult result=null;
	private String id=null;
	@Resource(name="baseService")
	private BaseService<Employee> employeeManager;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("save")
	public String create(){
		employeeManager.save(employee);
		result = new OperationResult(true,"新建员工成功");
		return "result";
	}
	@Action("update")
	public String update(){
		employeeManager.update(employee);
		result = new OperationResult(true, "修改员工成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		employeeManager.remove(id,new Employee());
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
	@Override
	public Employee getModel() {
		if(employee==null){
			employee=new Employee();
		}
		return employee;
	}
}
