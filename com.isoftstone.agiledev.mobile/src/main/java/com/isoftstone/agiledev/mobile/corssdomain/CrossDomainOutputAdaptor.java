package com.isoftstone.agiledev.mobile.corssdomain;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.isoftstone.agiledev.core.mobile.CallbackData;
import com.isoftstone.agiledev.web.springmvc.DataOutputAdaptor;

public class CrossDomainOutputAdaptor implements DataOutputAdaptor {

	
	private ObjectMapper objectMapper = new ObjectMapper();
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		
		try {
			
			CallbackData data = (CallbackData) obj;
//			JsonGenerator generator =
//					objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
//			SerializationConfig config = objectMapper.getSerializationConfig();
			
			String value = objectMapper.writeValueAsString(data.getObject());
//			objectMapper.writeValue(generator, String.format("%s(%s)", data.getCallback(),value));
			ServletOutputStream stream = response.getOutputStream();
			stream.print( String.format("%s(%s)", data.getCallback(),value));
			stream.flush();
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public boolean check(Object o) {
		if(o==null)return false;
		if(type!=null)
			return o.getClass().getName().equals(this.type);
		return o instanceof CallbackData;
	}

	private String type;
	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

}

