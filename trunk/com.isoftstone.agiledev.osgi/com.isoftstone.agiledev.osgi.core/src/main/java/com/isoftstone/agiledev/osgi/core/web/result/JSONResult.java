package com.isoftstone.agiledev.osgi.core.web.result;
/**
 * 将对象或者集合输出为json,编码utf-8
 */
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.ActionContext;


public class JSONResult implements Result {

    private static Logger logger = LoggerFactory.getLogger(JSONResult.class);
    
    @SuppressWarnings({"unchecked"})
	protected Object getRoot(Action action,Object o) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{

		Object root = null;
		Map<String, String> params = (Map<String, String>) o;
		for (String s : params.keySet()) {
			if(s.equals("root")){
				String methodName = params.get(s);
				methodName = "get"+methodName.substring(0,1).toUpperCase()+methodName.substring(1);
				root = action.getClass().getDeclaredMethod(methodName, null).invoke(action, null);
//				root = BeanUtils.getProperty(action, params.get(s));
				break;
			}
		}
		return root;
    }
    
	public void execute(Action action,Object o) {
		try {
			logger.info("json result output from action ["+action+"]");
			
			Object root = this.getRoot(action, o);
			
			if(root==null){
				logger.warn("root is null!");
				return;
			}
			
			String json = null;
			if(root instanceof List<?>){
				JSONArray array = JSONArray.fromObject(root);
				json = array.toString();
			}else{
				JSONObject jsonObject = JSONObject.fromObject(root);
				json = jsonObject.toString();
			}
			this.printJSON(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void printJSON(String json) throws Exception{
		if(json==null || "".equals(json)){
			throw new Exception("json must not be null!");
		}
		logger.debug("output json is:"+json);
		HttpServletResponse response = (HttpServletResponse) ActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter outter = response.getWriter();
		outter.print(json);
		outter.flush();
		outter.close();
	}

}
