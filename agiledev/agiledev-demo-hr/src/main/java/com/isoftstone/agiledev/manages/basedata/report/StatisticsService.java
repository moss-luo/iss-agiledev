package com.isoftstone.agiledev.manages.basedata.report;

import java.util.List;

import com.isoftstone.agiledev.actions.basedata.report.StatisticsData;

public interface StatisticsService {

	/**
	 * 获取部门统计数据的接口
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsData> getDepStatisticsData()throws Exception;
	
	
	/**
	 * 获取职级统计的接口
	 * @return
	 * @throws Exception
	 */
	public List<StatisticsData> getLevelStatisticsData()throws Exception;
}
