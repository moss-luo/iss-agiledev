package com.isoftstone.agiledev.web.springmvc.easyui;

import com.isoftstone.agiledev.core.init.InitField;


public class EasyUIInitField implements InitField {

	/**
	 * 表单元素名称
	 */
	private String name;
	/**
	 * 初始化数据
	 */
	private Object initValue;
	/**
	 * 验证类型
	 */
	private String validate;
	/**
	 * 是否非空
	 */
	private boolean required;
	/**
	 * 默认消息
	 */
	private String defMessage;

	public String getDefMessage() {
		return defMessage;
	}

	public void setDefMessage(String defMessage) {
		this.defMessage = defMessage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getInitValue() {
		return initValue;
	}

	public void setInitValue(Object initValue) {
		this.initValue = initValue;
	}

	public String getValidate() {
		return validate;
	}

	public void appendValidate(String validate) {
		// if(validate!=null)
		// this.validate = (this.validate==null?validate:this.validate +
		// validate + ";");
		this.validate = validate;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public void setValidate(String validate) {
		this.validate = validate;
	}
}
