package com.isoftstone.agiledev.actions.basedata.report;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.isoftstone.agiledev.manages.basedata.report.AnalysisService;
import com.opensymphony.xwork2.ActionSupport;

public class AnalysisDepartmentAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private AnalysisService service;
	
	private List<ReportData> datas=null;
	
	@Override
	@Action(results={@Result(name="analysisDepartment",type="json",params={"root","datas"})})
	public String execute() 
	{
		datas=new ArrayList<ReportData>();
		
		try 
		{
			datas=service.getDepReportData();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		return "analysisDepartment";

	}

	public AnalysisService getService() {
		return service;
	}

	public void setService(AnalysisService service) {
		this.service = service;
	}

	public List<ReportData> getDatas() {
		return datas;
	}

	public void setDatas(List<ReportData> datas) {
		this.datas = datas;
	}
}
