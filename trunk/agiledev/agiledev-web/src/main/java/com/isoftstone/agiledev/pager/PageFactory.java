package com.isoftstone.agiledev.pager;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;


import org.apache.commons.logging.LogFactory;
import com.isoftstone.agiledev.pager.easyui.EasyuiPaginationStrategy;

public class PageFactory {
	
	
	private static Log log = LogFactory.getLog(PageFactory.class); 
	
	private static PageStrategy st = null;
	
	private static String defStrategy = null;
	
	static{
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("pager.properties"));
			defStrategy = prop.getProperty("pageStrategy");
		} catch (Exception e) {
			log.error("",e);
		}

	}
	
	/**
	 * 按照不同的分页策略生成页面结果
	 * @param strategy
	 * @param rsMap
	 * @return
	 */
	public static PageResult getPageResult(String strategy,Map<String, Object> rsMap){
		if(null==strategy){
			strategy = defStrategy;
		}
		try {
			st = (PageStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			log.error("分页策略类未找到，采用默认",e);
			st = new EasyuiPaginationStrategy();
		} 
		
		return st.getPageResult(rsMap);
	}
	
	/**
	 * 按不同策略获取分页查询参数
	 * @param strategy
	 * @param params 参数
	 * @return
	 */
	public static Map<String, Object> getPaginationParams(String strategy,Set<String> queryKey,Map<String, Object> params) {
		if(null==strategy){
			strategy = defStrategy;
		}		
		try {
			st = (PageStrategy) Class.forName(strategy).newInstance();
		} catch (Exception e) {
			log.error("分页策略类未找到，采用默认",e);
			st = new EasyuiPaginationStrategy();
		} 
		
		return st.getPaginationParams(queryKey,params);
	}
}
