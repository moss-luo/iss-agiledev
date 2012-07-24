package com.isoftstone.agiledev.osgi.core.web.result;

/**
 * ligerUI-grid格式的jsonResult。
 * @author david
 */
import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.SummaryActionProvider;

public class GridJSONResult extends JSONResult {
	private static Logger logger = LoggerFactory.getLogger(JSONResult.class);

	@Override
	public void execute(Action action, Object o) {
		logger.info("json result output from action [" + action + "]");
		try {
			Object root = this.getRoot(action, o);
			
			if(root==null){
				logger.warn("root is null!");
				return;
			}

			JSONArray array = JSONArray.fromObject(root);
			String json = array.toString();
			if (action instanceof SummaryActionProvider) {
				json = String.format("{\"Total\": %d, \"Rows\": %s}",
						((SummaryActionProvider) action).getTotal(), json);
			}
			this.printJSON(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
