package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.isoftstone.agiledev.core.query.QueryResult;
import com.isoftstone.agiledev.web.easyui.EasyUIGridData;

public class EasyUIGridDataOutputAdaptor implements DataOutputAdaptor {	
	private String type;
	private ObjectMapper objectMapper = new ObjectMapper();

	public EasyUIGridDataOutputAdaptor() {
		this.type = QueryResult.class.getName();
	}
	
	@Override
	public void output(HttpServletRequest requset,HttpServletResponse response, Object obj) {
		try {
			JsonGenerator generator =
					objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
			SerializationConfig config = objectMapper.getSerializationConfig();
			Object o = this.filterGridData(obj);

			objectMapper.writeValue(generator, o, config);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public boolean check(Object o) {
		return o instanceof QueryResult;
	}
	@SuppressWarnings("rawtypes")
	private Object filterGridData(Object queryResult) {
		if(queryResult instanceof Map){
			Map m = (Map) queryResult;
			for (Object o : m.values()) {
				if(check(o)){
					return convertToEasyUIGridData((QueryResult<?>)o);
				}
			}
		}else{
			if(!check(queryResult))throw new RuntimeException("format error:"+queryResult);
			return convertToEasyUIGridData((QueryResult<?>)queryResult);
		}
		return null;
	}
	private EasyUIGridData convertToEasyUIGridData(QueryResult<?> queryResult){
		EasyUIGridData d = new EasyUIGridData();
		d.setRows(queryResult.getData());
		d.setTotal(queryResult.getTotal());
		
		return d;
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
