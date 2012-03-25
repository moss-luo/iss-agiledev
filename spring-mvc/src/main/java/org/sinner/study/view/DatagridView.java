package org.sinner.study.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.codehaus.jackson.JsonEncoding;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class DatagridView extends MappingJacksonJsonView {
	protected JsonEncoding encoding = JsonEncoding.UTF8;
	@Override
	public void setEncoding(JsonEncoding encoding) {
		this.encoding = encoding;
	}
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Object temp = model.get("datagrid-json");
		if(temp==null){
			throw new Exception("There is no renderedAttribute named 'datagrid-json' in this response!");
		}
		Object controller = model.get("controller");
		if(controller == null){
			throw new Exception("inject controller fialed");
		}
		if(!(controller instanceof SummerProvider)){
			throw new Exception("Thr controller which returned type of datagridjson,it must be implement SummerProvider");
		}
		SummerProvider summer = (SummerProvider) controller;
		String json = JSONArray.fromObject(temp).toString();
		response.setContentType("application/json");
		response.setCharacterEncoding(this.encoding.getJavaName());
		PrintWriter outter = response.getWriter();
		outter.print(String.format("{\"total\":\"%d\",rows\":%s}",summer.getTotal(),json.toString()));
		outter.flush();
		outter.close();
	}
}
