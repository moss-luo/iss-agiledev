package com.isoftstone.agiledev.initform;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;

public abstract class AbstractResult implements Result{
	
	@Override
	public abstract void doResult(HttpServletResponse response, ActionInvocation action) ;
	
	/**
	 * 添加初始字段
	 * @param f
	 */
	protected void pushFieldInit(InitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			field.setInitValue(f.getInitValue());
		}else{
			InitData.pushInitData(f);
		}
	}
	/**
	 * 添加字段验证类型
	 * @param f
	 */
	protected void pushFieldValidate(InitField f){
		InitField field = this.getField(f.getName());
		if(field!=null){
			field.appendValidate(f.getValidate());
			field.setRequired(f.isRequired());
		}else{
			InitData.pushInitData(f);
		}
	}
	protected InitField getField(String fieldName){
		for (InitField field :InitData.getInitData()) {
			if(field.getName().equals(fieldName))
				return field;
		}
		return null;
	}

}
