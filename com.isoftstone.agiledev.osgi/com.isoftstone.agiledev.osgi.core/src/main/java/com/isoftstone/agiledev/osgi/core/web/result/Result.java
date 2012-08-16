package com.isoftstone.agiledev.osgi.core.web.result;

import com.isoftstone.agiledev.osgi.core.web.Action;

/**
 * result接口，提供给所有bundle中的action使用
 */

public interface Result {

	void execute(Action action,Object o) throws Exception ;
}
