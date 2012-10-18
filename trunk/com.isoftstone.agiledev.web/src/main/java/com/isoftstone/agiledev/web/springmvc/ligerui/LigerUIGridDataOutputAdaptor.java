package com.isoftstone.agiledev.web.springmvc.ligerui;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.isoftstone.agiledev.core.query.QueryResult;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;

public class LigerUIGridDataOutputAdaptor implements DataOutputAdaptor{

	private String type;
	private ObjectMapper objectMapper = new ObjectMapper();
	public LigerUIGridDataOutputAdaptor() {
		this.type=QueryResult.class.getName();
	}
	@Override
	public void output(HttpServletRequest request, HttpServletResponse response, Object obj) {
		try {
			JsonGenerator generator =
					objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
			SerializationConfig config = objectMapper.getSerializationConfig();
			Object o = this.filterGridData(obj);
//			generator.writeString(o.toString());
//			objectMapper.writeValue(generator, o, config);
			ServletOutputStream stream = response.getOutputStream();
			stream.print(o.toString());
			stream.flush();
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private Object filterGridData(Object queryResult){
		if(queryResult instanceof Map){
			Map map = (Map)queryResult;
			for(Object o:map.values()){
				return convertToLigerUIGridData((QueryResult<?>)o);
			}
		}else{
			if(!check(queryResult))throw new RuntimeException("format error:"+queryResult);
			return convertToLigerUIGridData((QueryResult<?>)queryResult);
		}
		return null;
	}

	private String convertToLigerUIGridData(QueryResult<?> queryResult){
		/**ligerUI特有属性转换输出**/
		String json = String.format("{\"Total\": %d, \"Rows\": %s}",
				queryResult.getTotal(), JsonUtil.listToJson(queryResult.getData()).toString());
		
		return json;
	}
	
	@Override
	public boolean check(Object o) {
		return o instanceof QueryResult;
	}

	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

}
