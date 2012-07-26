package com.isoftstone.agiledev.actions.basedata.report;

import java.text.DecimalFormat;

/**
 *  数据报告bean
 *  用于：部门/职级 分析
 * @author bjren
 *
 */
public class ReportData {

	private String name;
	private int totalEmployee;
	private int totalPeriod;
	private int distinctEmployee;
	private int curEmployee;
	
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
	public int getCurEmployee() {
		return curEmployee;
	}
	public void setCurEmployee(int curEmployee) {
		this.curEmployee = curEmployee;
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
}
