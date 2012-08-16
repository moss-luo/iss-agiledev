package com.isoftstone.agiledev.osgi.core.web.request;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import com.isoftstone.agiledev.osgi.core.web.ValueStack;

@SuppressWarnings({ "rawtypes", "serial" })
public class DefaultValueStack extends HashMap implements ValueStack {

	private Map<String,String> params = new Hashtable<String,String>();
	@Override
	public Object get(String parameterName) {
		return this.params.get(parameterName);
	}

	@Override
	public void put(String parameterName, String value) {
		this.params.put(parameterName, value);
	}


}
