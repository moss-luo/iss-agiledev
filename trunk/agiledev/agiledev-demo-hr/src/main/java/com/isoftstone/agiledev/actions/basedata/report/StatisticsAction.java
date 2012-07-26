package com.isoftstone.agiledev.actions.basedata.report;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;


import com.isoftstone.agiledev.manages.basedata.report.StatisticsService;
import com.opensymphony.xwork2.ActionSupport;


public class StatisticsAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private List<StatisticsData> depResults=null;
	
	private List<StatisticsData> rankResults=null;
	@Resource
	private StatisticsService service;
	
	@Action(results={@Result(name="statisticsDepartment",type="json",params={"root","depResults"})})
	public String execute()
	{
		try 
		{
			List<StatisticsData> sds=service.getDepStatisticsData();
			depResults=transResult(sds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "statisticsDepartment";
	}
	
	@Action(value="statisticsrank",results={@Result(name="statisticsRank",type="json",params={"root","rankResults"})})
	public String StatisticsRank()
	{
		try 
		{
			List<StatisticsData> sds=service.getLevelStatisticsData();
			rankResults=transResult(sds);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "statisticsRank";
	}
	
	
	/**
     * 将“部门统计的基础数据”进行转换封装成“部门培训统计的数据结果集”
     * @param result 部门统计的基础数据
     * @return 部门培训统计的数据结果集
     */
    private List<StatisticsData> transResult(List<StatisticsData> result) {
    	
    	Total total=new Total();
    	
    	for(int i=0;i<result.size();i++)
    	{
    		StatisticsData sd=result.get(i);
    		total.setAllTotalEmployee(total.getAllTotalEmployee()+sd.getTotalEmployee());
    		total.setAllTotalPeriod(total.getAllTotalPeriod()+sd.getTotalPeriod());
    		total.setAllDistinctEmployee(total.getAllDistinctEmployee()+sd.getDistinctEmployee());
    		total.setAllCurEmployee(total.getAllCurEmployee()+sd.getCurEmployee());
    	}
    	result.get(result.size()-1).setAlltotalEmployee(total.getAllTotalEmployee());
    	result.get(result.size()-1).setAlltotalPeriod(total.getAllTotalPeriod());
    	result.get(result.size()-1).setAlldistinctEmployee(total.getAllDistinctEmployee());
    	result.get(result.size()-1).setAllcurEmployee(total.getAllCurEmployee());
    	
    	return result;
    }


	public List<StatisticsData> getDepResults() {
		return depResults;
	}

	public void setDepResults(List<StatisticsData> depResults) {
		this.depResults = depResults;
	}

	public List<StatisticsData> getRankResults() {
		return rankResults;
	}

	public void setRankResults(List<StatisticsData> rankResults) {
		this.rankResults = rankResults;
	}

	public StatisticsService getService() {
		return service;
	}

	public void setService(StatisticsService service) {
		this.service = service;
	}
	
}
