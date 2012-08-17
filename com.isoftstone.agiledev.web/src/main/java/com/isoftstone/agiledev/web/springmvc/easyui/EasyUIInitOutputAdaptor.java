package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.isoftstone.agiledev.core.init.DefaultInitChain;
import com.isoftstone.agiledev.core.init.InitData;
import com.isoftstone.agiledev.core.init.InitField;
import com.isoftstone.agiledev.core.init.InitializeAdaptor;
import com.isoftstone.agiledev.core.init.InitializeModel;
import com.isoftstone.agiledev.core.init.InitializeModelOutputAdaptor;

public class EasyUIInitOutputAdaptor implements DataOutputAdaptor,InitializeModelOutputAdaptor{

	private List<InitializeAdaptor> initSupporters = new ArrayList<InitializeAdaptor>();
	private ObjectMapper objectMapper = new ObjectMapper();
//	private SerializationConfig config = objectMapper.getSerializationConfig();
	
	public EasyUIInitOutputAdaptor(){
		initSupporters.add(new EasyUIFormInitial());
		initSupporters.add(new EasyUIValidateInitial());
	}
	@Override
	public void writeToInitForm(HttpServletResponse response,
			List<? extends InitField> initFields) {
	}
	@Override
	public void output(HttpServletRequest request,
			HttpServletResponse response, Object obj) {
		try {
			if(check(obj)){
				InitializeModel model = (InitializeModel) obj;
				for(int i=0;i<initSupporters.size();i++){
					if(i+1>=initSupporters.size()){
						initSupporters.get(i).doInit(request,model,new DefaultInitChain(null));
						break;
					}else{
						initSupporters.get(i).doInit(request,model,new DefaultInitChain(initSupporters.get(i+1)));
					}
					
				}
//				JsonGenerator generator =
//						objectMapper.getJsonFactory().createJsonGenerator(response.getOutputStream(), JsonEncoding.UTF8);
//				
//				config.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
//				objectMapper.writeValue(generator, InitData.getInitData(), config);
				String json = objectMapper.writeValueAsString(InitData.getInitData());

				ServletOutputStream stream = response.getOutputStream();
				stream.print(String.format("{\"field\":%s}",json));
				stream.flush();
				stream.close();
				InitData.clear();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean check(Object o) {
		if(o==null)return false;
		if(o.getClass().getName().equals(this.type))return true;
		return false;
	}

	private String type;
	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return this.type;
	}
	public void setInitSupporters(List<InitializeAdaptor> initSupporters) {
		this.initSupporters = initSupporters;
	}
	

}