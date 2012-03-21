package com.isoftstone.agiledev.initform;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * 构造初始化数据的抽象接口
 * @author sinner
 *
 */
public interface Result {

	void doResult(HttpServletResponse response,ActionInvocation action);
}
