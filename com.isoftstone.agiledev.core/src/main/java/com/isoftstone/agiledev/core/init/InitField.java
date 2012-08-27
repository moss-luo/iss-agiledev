package com.isoftstone.agiledev.core.init;

public interface InitField {
	/**
	 * 页面字段名称
	 * @return
	 */
	String getName();
	void setName(String name);
	/**
	 * 是否非空
	 * @return
	 */
	public boolean isRequired();
	public void setRequired(boolean required);
}