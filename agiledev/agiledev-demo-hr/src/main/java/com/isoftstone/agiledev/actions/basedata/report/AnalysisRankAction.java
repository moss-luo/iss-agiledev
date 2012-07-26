package com.isoftstone.agiledev.actions.basedata.report;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.isoftstone.agiledev.manages.basedata.report.AnalysisService;
import com.opensymphony.xwork2.ActionSupport;

public class AnalysisRankAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private List<ReportData> datas=null;
	
	@Resource
	private AnalysisService service;

	@Override
	@Action(results={@Result(name="analysisRank",type="json",params={"root","datas"})})
	public String execute() 
	{
		try {
			datas=service.getLevelReportData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "analysisRank";
	}

	public List<ReportData> getDatas() {
		return datas;
	}

	public void setDatas(List<ReportData> datas) {
		this.datas = datas;
	}

	public AnalysisService getService() {
		return service;
	}

	public void setService(AnalysisService service) {
		this.service = service;
	}
}
