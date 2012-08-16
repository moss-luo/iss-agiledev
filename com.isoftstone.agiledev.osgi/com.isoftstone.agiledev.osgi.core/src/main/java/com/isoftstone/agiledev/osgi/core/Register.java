package com.isoftstone.agiledev.osgi.core;


public interface Register {

	void start()throws Exception;
	void stop()throws Exception;
	void setContext(Context context);
}
