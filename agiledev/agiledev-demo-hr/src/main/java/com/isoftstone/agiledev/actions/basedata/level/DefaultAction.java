package com.isoftstone.agiledev.actions.basedata.level;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.isoftstone.agiledev.OperationResult;
import com.isoftstone.agiledev.manages.BaseService;
import com.isoftstone.agiledev.validater.Validation;
import com.isoftstone.agiledev.validater.Validations;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;
@Results({
	@Result(name="result",type="json",params={"root","result","contentType","text/html"}),
	@Result(name="init",type="initjson")
})
public class DefaultAction implements ModelDriven<Level>{

	//@Validation
	//@Initialization
//	@Initialization({
//		@Initializa(fieldName="levelName",value="初级工程师")
//	})
	@Validations({
		@Validation(fieldName="levelName",validType=StringLengthFieldValidator.class,params={"message","职级名称必须在1-10之间","minLength","1","maxLength","10"})
	})
	private Level level=null;
	private OperationResult result=null;
	private String id=null;
	@Resource(name="baseService")
	private BaseService<Level> levelManager=null;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("create")
	public String create(){
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("levelName", level.getLevelName());
		if(!levelManager.unique(p, new Level())){
			result=new OperationResult(false,"该职级已存在");
			return "result";
		}
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
	@Override
	public Level getModel() {
		if(level==null)
		{
			level=new Level();
		}
		return level;
	}
}
