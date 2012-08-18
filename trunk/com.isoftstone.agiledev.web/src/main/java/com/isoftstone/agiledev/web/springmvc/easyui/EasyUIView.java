package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.eclipse.gemini.blueprint.context.BundleContextAware;
import org.osgi.framework.BundleContext;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

public class EasyUIView extends MappingJacksonJsonView implements BundleContextAware {
	private ObjectMapper objectMapper = new ObjectMapper();
	private boolean prefixJson = false;
	private JsonEncoding encoding = JsonEncoding.UTF8;
	private List<DataOutputAdaptor> dataOutputAdaptors = new ArrayList<DataOutputAdaptor>();
	private BundleContext bundleContext = null;
	
	
	private void init(){
		dataOutputAdaptors.add(new EasyUIGridDataOutputAdaptor());
		dataOutputAdaptors.add(new EasyUIInitOutputAdaptor(bundleContext));
		dataOutputAdaptors.add(new EasyUITreeDataOutputAdaptor());
		DataOutputAdaptorFactory.buildFactory(this.dataOutputAdaptors);
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonGenerator generator =
				objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), encoding);
		if (prefixJson) {
			generator.writeRaw("{} && ");
		}
		
		SerializationConfig config = objectMapper.getSerializationConfig();
		
		DataOutputAdaptor adaptor = this.buildDataOutputAdaptor(model);
		
		Object value = filterModel(model, generator, config, request);
		
//		objectMapper.writeValue(generator, value, config);

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
//			if (isTreeData(singleObj)) {
//				return filterTreeData(config, singleObj, request);
//			}
			return singleObj;
		}
		
		return filteredModel;
	}
	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	private DataOutputAdaptor buildDataOutputAdaptor(Object model) {
		if (this.dataOutputAdaptors == null) {
			return null;
		} else if(model instanceof Map){
			Map m = (Map) model;
			Iterator<Map.Entry> it = m.entrySet().iterator();
			while(it.hasNext()){
				DataOutputAdaptor baseAdaptor = DataOutputAdaptorFactory.getOutputAdaptor(it.next().getValue().getClass().getName());
				if(baseAdaptor!=null)return baseAdaptor;
			}
		}else{
			for (DataOutputAdaptor f : this.dataOutputAdaptors) {
				String formatType = f.getType();
				if (model.getClass().getName().equals(formatType))
					return f;
			}
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

	public void setDataOutputAdaptors(List<DataOutputAdaptor> dataOutputAdaptors) {
		this.dataOutputAdaptors.addAll(dataOutputAdaptors);
		DataOutputAdaptorFactory.buildFactory(this.dataOutputAdaptors);
	}

	@Override
	public void setBundleContext(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
		this.init();
	}
}
