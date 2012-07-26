package com.isoftstone.agiledev.manages.basedata.report;

import java.util.List;

import com.isoftstone.agiledev.actions.basedata.report.ReportData;

public interface AnalysisService {

	/**
	 * 获取部门分析数据的接口
	 * @return
	 * @throws Exception
	 */
	public List<ReportData> getDepReportData()throws Exception;
	
	
	/**
	 * 获取职级分析数据的接口
	 * @return
	 * @throws Exception
	 */
	public List<ReportData> getLevelReportData()throws Exception;
	
}
