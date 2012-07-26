package com.isoftstone.agiledev.manages.basedata.report;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.isoftstone.agiledev.actions.basedata.report.StatisticsData;
import com.isoftstone.agiledev.dao.BaseDao;

@Service
public class StatisticsServiceImpl implements StatisticsService{

	@Resource
	private BaseDao bd;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticsData> getDepStatisticsData() throws Exception 
	{
		return bd.list(null, "com.isoftstone.agiledev.hr.mapper.ReportMapper.getDepStatisticesData");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticsData> getLevelStatisticsData() throws Exception 
	{
		return bd.list(null, "com.isoftstone.agiledev.hr.mapper.ReportMapper.getLevelStatisticesData");
	}

	public BaseDao getBd() {
		return bd;
	}

	public void setBd(BaseDao bd) {
		this.bd = bd;
	}
}
