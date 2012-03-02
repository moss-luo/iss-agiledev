package com.isoftstone.agiledev.actions.basedata.level;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.manages.BaseService;

public class DefaultAction {

	private Level level;
	private OperationResult result;
	private String id;
	@Resource(name="baseService")
	private BaseService<Level> levelManager;
	@Action("create")
	public String create(){
		levelManager.save(level);
		result = new OperationResult(true,"创建职级成功");
		return "result";
	}
	@Action("update")
	public String update(){
		levelManager.update(level);
		result = new OperationResult(true, "修改职级成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		levelManager.remove(id,new Level());
		result = new OperationResult(true, "删除职级成功");
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
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
}
