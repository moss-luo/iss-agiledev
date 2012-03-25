package org.sinner.study.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
		Object value = filterModel(model);
//		JsonGenerator generator =
//				objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
//		if (prefixJson) {
//			generator.writeRaw("{} && ");
//		}
//		objectMapper.writeValue(generator, value);
		
		String json = JSONObject.fromObject(value).toString();
		response.setContentType("application/json;"+this.encoding);
		response.setCharacterEncoding(this.encoding.getJavaName());
		PrintWriter outter = response.getWriter();
		outter.print(String.format("{\"\"total\":\"%d\",rows\":%s}",100,json.toString()));
		outter.flush();
		outter.close();
	}
}
