package com.isoftstone.agiledev.osgi.core.inject;

import java.util.Map;

public interface ClassLoader {
	Object loadClass(String name);
	Object loadClass(String name,Map<String,String> condition);
}
