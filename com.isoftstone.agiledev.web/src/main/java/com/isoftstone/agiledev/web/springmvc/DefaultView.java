package com.isoftstone.agiledev.web.springmvc;


import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.isoftstone.agiledev.web.springmvc.easyui.EasyUIDataOutputAdaptorProvider;

public class DefaultView extends MappingJacksonJsonView{

	private static final DataOutputAdaptorProvider DEFAULT_ADAPTOR_PROVIDER = new EasyUIDataOutputAdaptorProvider();
	private DataOutputAdaptorProvider dataOutputAdaptorProvider = DEFAULT_ADAPTOR_PROVIDER;
	

	
	public void setDataOutputAdaptorProvider(DataOutputAdaptorProvider dataOutputAdaptorProvider) {
		this.dataOutputAdaptorProvider = dataOutputAdaptorProvider;
	}

	public DataOutputAdaptorProvider getDataOutputAdaptorProvider() {
		dataOutputAdaptorProvider.init();
		return dataOutputAdaptorProvider;
	}

	private ObjectMapper objectMapper = new ObjectMapper();
	private boolean prefixJson = false;
	private JsonEncoding encoding = JsonEncoding.UTF8;
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if(request.getHeader("Accept").indexOf("application/json")==-1){
			response.setContentType("text/html");
		}
		JsonGenerator generator =
				objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
		if (prefixJson) {
			generator.writeRaw("{} && ");
		}
		
		SerializationConfig config = objectMapper.getSerializationConfig();
		
		DataOutputAdaptor adaptor = this.buildDataOutputAdaptor(model);
		
		Object value = filterModel(model, generator, config, request);
		
		if (adaptor != null) {
			adaptor.output(request,response, value);
		} else {
			objectMapper.writeValue(generator, value, config);
		}
	}
	
	protected Object filterModel(Map<String, Object> model, JsonGenerator generator,
			SerializationConfig config, HttpServletRequest request) {
		Object result = super.filterModel(model);
		
		if (!(result instanceof Map)) {
			return result;
		}
		
		@SuppressWarnings("unchecked")
		Map<String, Object> filteredModel = (Map<String, Object>)result;		
		if (filteredModel.size() == 1) {
			Object singleObj = filteredModel.values().iterator().next();
			return singleObj;
		}
		
		return filteredModel;
	}
	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DataOutputAdaptor buildDataOutputAdaptor(Object model) {
		if(model instanceof Map){
			Map m = (Map) model;
			Iterator<Map.Entry> it = m.entrySet().iterator();
			while(it.hasNext()){
				DataOutputAdaptor baseAdaptor = this.getDataOutputAdaptorProvider().getAdaptor(it.next().getValue().getClass().getName());
				if(baseAdaptor!=null)return baseAdaptor;
			}
		}else{
//			for (DataOutputAdaptor f : this.dataOutputAdaptors) {
//				String formatType = f.getType();
//				if (model.getClass().getName().equals(formatType))
//					return f;
//			}
		}
		return null;
	}
	@Override
	public void setPrefixJson(boolean prefixJson) {
		this.prefixJson = prefixJson;
	}
	
	@Override
	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@Override
	public void setEncoding(JsonEncoding encoding) {
		this.encoding = encoding;
	}
}
