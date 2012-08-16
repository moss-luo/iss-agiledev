package com.isoftstone.agiledev.osgi.core.web;

public interface ValueStack {

	  Object get(String parameterName);
	  void put(String parameterName,String value);


}
