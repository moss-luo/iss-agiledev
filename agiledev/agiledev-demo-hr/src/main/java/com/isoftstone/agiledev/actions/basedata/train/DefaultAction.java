package com.isoftstone.agiledev.actions.basedata.train;


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
public class DefaultAction implements ModelDriven<Train>{

	//@Validation
	//@Initialization
//	@Initialization({
//		@Initializa(fieldName="trainName",value="内部培训")
//	})
	@Validations({
		@Validation(fieldName="trainName",validType=StringLengthFieldValidator.class,params={"message","培训类别名必须在1-10之间","minLength","1","maxLength","10"})
	})
	private Train train=null;
	private OperationResult result=null;
	private String id=null;
	@Resource(name="baseService")
	private BaseService<Train> trainManager=null;
	@Action("init")
	public String init(){
		return "init";
	}
	@Action("save")
	public String create(){
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("uid",train.getUid());
		if(!trainManager.unique(p, new Train())){
			result=new OperationResult(true,"该培训类别已存在");
			return "result";
		}
		trainManager.save(train);
		result=new OperationResult(true,"创建培训类别成功");
		return "result";
	}
	@Action("update")
	public String update(){
		trainManager.update(train);
		result=new OperationResult(true,"修改培训类别成功");
		return "result";
	}
	@Action("remove")
	public String remove(){
		trainManager.remove(id,new Train());
		result=new OperationResult(true,"删除培训类别成功");
		return "result";
	}
	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
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

	@Override
	public Train getModel() {
        if(train==null){
        	train=new Train();
        }
		return train;
	}
}
