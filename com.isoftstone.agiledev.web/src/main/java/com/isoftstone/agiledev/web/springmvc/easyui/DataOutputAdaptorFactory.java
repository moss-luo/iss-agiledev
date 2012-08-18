package com.isoftstone.agiledev.web.springmvc.easyui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataOutputAdaptorFactory {

	private static Map<String,DataOutputAdaptor> outputAdaptorFactory = new HashMap<String, DataOutputAdaptor>();
	
	public static void buildFactory(List<DataOutputAdaptor> adaptors){
		for (DataOutputAdaptor d : adaptors) {
			if(!outputAdaptorFactory.containsKey(d.getType()))
				outputAdaptorFactory.put(d.getType(), d);
		}
	}
	
	public static DataOutputAdaptor getOutputAdaptor(String type){
		return outputAdaptorFactory.get(type);
	}
	
	
}
