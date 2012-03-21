package com.isoftstone.agiledev.initform;

public class InitField {
	

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
	

	public InitField() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public InitField(String name){
		this(name,null,null,false);
	}
	public InitField(String name,Object initValue){
		this(name,initValue,null,false);
	}
	
	public InitField(String name, Object initValue, String validate,boolean required) {
		super();
		this.name = name;
		this.initValue = initValue;
		this.validate = validate;
		this.required = required;
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
		if(validate!=null)
			this.validate = (this.validate==null?validate:this.validate + validate + ";");
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
