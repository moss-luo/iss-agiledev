package com.isoftstone.agiledev.manages.basedata.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.isoftstone.agiledev.actions.basedata.report.ReportData;
import com.isoftstone.agiledev.dao.BaseDao;

@Service
public class AnalysisServiceImpl  implements AnalysisService{

	@Resource
	private BaseDao dao;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReportData> getDepReportData() throws Exception 
	{
		return dao.list(null, "com.isoftstone.agiledev.hr.mapper.ReportMapper.getDepReportData");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReportData> getLevelReportData() throws Exception 
	{
		return dao.list(null, "com.isoftstone.agiledev.hr.mapper.ReportMapper.getLevelReportData");
	}

	public BaseDao getDao() {
		return dao;
	}

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}
}
