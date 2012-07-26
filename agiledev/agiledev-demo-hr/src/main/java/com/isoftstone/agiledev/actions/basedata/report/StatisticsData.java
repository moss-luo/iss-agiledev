package com.isoftstone.agiledev.actions.basedata.report;

import java.text.DecimalFormat;

/**
 * 统计的bean
 * 用于统计报表
 * @author bjren
 *
 */
public class StatisticsData {
	
	private String name; //培训或职级的名称
	private int totalEmployee; //培训员工人次
	private int totalPeriod; //培训课时
	private int distinctEmployee; //培训员工人数
	private int curEmployee; //部门或职级员工总数
	
	private int alltotalEmployee;// 合计所有部门总培训员工人次
	private int alltotalPeriod;// 合计所有部门总培训课时
	private int alldistinctEmployee;// 合计所有部门总培训员工人数
	private int allcurEmployee;// 合计所有部门员工总数
	
	public int getAlltotalEmployee() {
		return alltotalEmployee;
	}
	public void setAlltotalEmployee(int alltotalEmployee) {
		this.alltotalEmployee = alltotalEmployee;
	}
	public int getAlltotalPeriod() {
		return alltotalPeriod;
	}
	public void setAlltotalPeriod(int alltotalPeriod) {
		this.alltotalPeriod = alltotalPeriod;
	}
	public int getAlldistinctEmployee() {
		return alldistinctEmployee;
	}
	public void setAlldistinctEmployee(int alldistinctEmployee) {
		this.alldistinctEmployee = alldistinctEmployee;
	}
	public int getAllcurEmployee() {
		return allcurEmployee;
	}
	public void setAllcurEmployee(int allcurEmployee) {
		this.allcurEmployee = allcurEmployee;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTotalEmployee() {
		return totalEmployee;
	}
	public void setTotalEmployee(int totalEmployee) {
		this.totalEmployee = totalEmployee;
	}
	public int getTotalPeriod() {
		return totalPeriod;
	}
	public void setTotalPeriod(int totalPeriod) {
		this.totalPeriod = totalPeriod;
	}
	public int getDistinctEmployee() {
		return distinctEmployee;
	}
	public void setDistinctEmployee(int distinctEmployee) {
		this.distinctEmployee = distinctEmployee;
	}
	public int getCurEmployee() {
		return curEmployee;
	}
	public void setCurEmployee(int curEmployee) {
		this.curEmployee = curEmployee;
	}
	public String getAllAvgPeriod() {
		if(allcurEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00");
			return f.format(Double.valueOf(alltotalPeriod).doubleValue()
					/ Long.valueOf(allcurEmployee).doubleValue()).toString();
		} else {
			return "000.00";
		}
	}
	public String getAllCoverageRate() {
		if(allcurEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00%");
			return f.format(Double.valueOf(alldistinctEmployee).doubleValue()
					/ Long.valueOf(allcurEmployee).doubleValue()).toString();
		} else {
			return "000.00%";
		}
	}
	
	public String getAvgPeriod() {
		if(curEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00");
			return f.format(Double.valueOf(totalPeriod).doubleValue()
					/ Long.valueOf(curEmployee).doubleValue()).toString();
		} else {
			return "000.00";
		}
	}
	public String getCoverageRate() {
		if(curEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00%");
			return f.format(Double.valueOf(distinctEmployee).doubleValue()
					/ Long.valueOf(curEmployee).doubleValue()).toString();
		} else {
			return "000.00%";
		}
	}
}
