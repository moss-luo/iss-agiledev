package com.isoftstone.agiledev.easyui.datagrid;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.json.JSONResult;

import com.isoftstone.agiledev.query.QueryManager;
import com.isoftstone.agiledev.query.QuerySupportAction;
import com.isoftstone.agiledev.query.SummaryProvider;
import com.opensymphony.xwork2.ActionInvocation;

public class PaginationSupportJSONResult extends JSONResult {
	private static final long serialVersionUID = 466372319865445138L;
	private Object action;

	@Override
	public void execute(ActionInvocation invocation) throws Exception {
		action = invocation.getAction();

		super.execute(invocation);
	}

	@Override
	protected void writeToResponse(HttpServletResponse response, String json,
			boolean gzip) throws IOException {
		if (action instanceof QuerySupportAction) {
			QueryManager queryManager = ((QuerySupportAction) action)
					.getQueryManager();
			json = String.format("{\"total\": %d, \"rows\": %s}",
					queryManager.getTotal(), json);
		} else if (action instanceof SummaryProvider) {
			json = String.format("{\"total\": %d, \"rows\": %s}",
					((SummaryProvider) action).getTotal(), json);
		}
		// response.setContentType("text/html;charset=UTF-8");
		// response.setHeader("Content-Type", "text/html; charset=UTF-8");
		// response.setCharacterEncoding( "UTF-8");
		// PrintWriter out = response.getWriter();
		// out.print(json);
		super.writeToResponse(response, json, gzip);
	}
	
}
