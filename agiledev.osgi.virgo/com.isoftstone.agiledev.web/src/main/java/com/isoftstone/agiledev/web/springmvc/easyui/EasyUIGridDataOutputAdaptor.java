package com.isoftstone.agiledev.web.springmvc.easyui;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.isoftstone.agiledev.core.datagrid.GridData;
import com.isoftstone.agiledev.web.DataOutputAdaptor;
import com.isoftstone.agiledev.web.easyui.EasyUIGridData;

public class EasyUIGridDataOutputAdaptor implements DataOutputAdaptor {

	private String type;
	private ObjectMapper objectMapper = new ObjectMapper();


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
		return o instanceof GridData;
	}
	@SuppressWarnings("rawtypes")
	private Object filterGridData(Object gridData) {
		if(gridData instanceof Map){
			Map m = (Map) gridData;
			for (Object o : m.values()) {
				if(check(o)){
					return convertToEasyUIGridData((GridData)o);
				}
			}
		}else{
			if(!check(gridData))throw new RuntimeException("format error:"+gridData);
			return convertToEasyUIGridData((GridData)gridData);
		}
		return null;
	}
	private EasyUIGridData convertToEasyUIGridData(GridData gridData){
		EasyUIGridData d = new EasyUIGridData();
		d.setRows(gridData.getData());
		d.setTotal(gridData.getTotal());
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
