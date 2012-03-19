package com.isoftstone.agiledev.actions.basedata.program;

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
public class DefaultAction implements ModelDriven<Program>{

	private Program program=null;
	
	private OperationResult result=null;
	
	private String id=null;
	
	@Resource(name="baseService")
	private BaseService<Program> programsManager=null;
	
	@Action("init")
	public String init()
	{
		return "init";
	}
	@Action("save")
	public String create(){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("uid",program.getUid());
		if(!programsManager.unique(p, new Program())){
			result=new OperationResult(true,"该项目已存在");
			return "result";
		}
		programsManager.save(program);
		result=new OperationResult(true,"创建项目别成功");
		return "result";
	}
	@Action("update")
	public String update(){
		programsManager.update(program);
		result=new OperationResult(true,"修改项目成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		programsManager.remove(id,new Program());
		result=new OperationResult(true,"删除项目成功");
		return "result";
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program programs) {
		this.program = programs;
	}

	public OperationResult getResult() {
		return result;
	}


	public void setResult(OperationResult result) {
		this.result = result;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BaseService<Program> getProgramsManager() {
		return programsManager;
	}

	public void setProgramsManager(BaseService<Program> programsManager) {
		this.programsManager = programsManager;
	}
	
	public Program getModel() 
	{
		if(program==null)
		{
			return new Program();
		}
		return program;
	}
}
