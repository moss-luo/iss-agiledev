package com.isoftstone.agiledev.initform;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * json格式输出对象初始化数据及验证信息
 * @author xwpu
 *
 */
@SuppressWarnings("serial")
public class InitSupportJSONResult extends JSONResult{
	private ActionInvocation actionInvocation = null;
	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		this.actionInvocation = invocation;
		super.execute(invocation);
	}
	private Result[] results = new Result[]{new ValidateResult(),new InitFormResult()};
	@Override
	protected void writeToResponse(HttpServletResponse response, String json, boolean gzip) throws IOException {

		for (Result r : results) {
			r.doResult(response, actionInvocation);
		}
		
//		JSONArray fields = JSONArray.fromObject(InitData.getInitData());
		String jsonData = null;
		try {
			jsonData = JSONUtil.serialize(InitData.getInitData());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType("text/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		out.print(String.format("{\"field\":%s}",jsonData));
		out.flush();
		out.close();
		InitData.clear();
		//super.writeToResponse(response, json, gzip);
		
		
	}
	public void init(Properties properties) {
		// TODO Auto-generated method stub
		
	}
	
}
