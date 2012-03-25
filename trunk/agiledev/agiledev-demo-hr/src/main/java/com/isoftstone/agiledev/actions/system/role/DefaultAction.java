package com.isoftstone.agiledev.actions.system.role;

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
/**
 * 角色的基础操作类,包含新增,修改,删除
 * @author Administrator
 *
 */
@Results({
	@Result(name = "result", type = "json", params = {"root", "result", "contentType", "text/html"}),
	@Result(name = "init", type = "initjson")
})
public class DefaultAction implements ModelDriven<Role>//,InitProvider{
{

	@Resource(name="roleService")
	private BaseService<Role> roleManager = null;
	
	
	private OperationResult result = null;
	@Validation
	@Initialization
	private Role role = null;
	private String id;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("create")
	public String create(){
		

		Map<String, Object> p = new HashMap<String, Object>();
		p.put("roleName", role.getRoleName());
		if(!roleManager.unique(p, role)){
			result = new OperationResult(false,"该角色已存在");
			return "result";
		}
		roleManager.save(role);
		result = new OperationResult(true, "添加角色成功");
		return "result";
	}
	@Action("update")
	public String update(){
		roleManager.update(role);
		result = new OperationResult(true, "修改角色成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		roleManager.remove(id,new Role());
		result = new OperationResult(true, "删除角色成功");
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
	@Override
	public Role getModel() {
		if(role == null)
			role = new Role();
		return role;
	}
}
