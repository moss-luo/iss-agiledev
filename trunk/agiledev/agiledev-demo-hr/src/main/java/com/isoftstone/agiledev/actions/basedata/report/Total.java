package com.isoftstone.agiledev.actions.basedata.report;


public class Total {

	private int allTotalEmployee; //所有培训人次
	private int allTotalPeriod; //所有的培训课时
	private int allDistinctEmployee; //所有的参与人数
	private int allCurEmployee; //所有的当前人数
	
	public int getAllTotalEmployee() {
		return allTotalEmployee;
	}
	public void setAllTotalEmployee(int allTotalEmployee) {
		this.allTotalEmployee = allTotalEmployee;
	}
	public int getAllTotalPeriod() {
		return allTotalPeriod;
	}
	public void setAllTotalPeriod(int allTotalPeriod) {
		this.allTotalPeriod = allTotalPeriod;
	}
	public int getAllDistinctEmployee() {
		return allDistinctEmployee;
	}
	public void setAllDistinctEmployee(int allDistinctEmployee) {
		this.allDistinctEmployee = allDistinctEmployee;
	}
	public int getAllCurEmployee() {
		return allCurEmployee;
	}
	public void setAllCurEmployee(int allCurEmployee) {
		this.allCurEmployee = allCurEmployee;
	}
	
	/*
	//所有培训的平均课时
	public String getAvgPeriod() {
		if(allCurEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00");
			return f.format(Double.valueOf(allTotalPeriod).doubleValue()
					/ Long.valueOf(allCurEmployee).doubleValue()).toString();
		} else {
			return "000.00";
		}
	}
	
	//所有培训的普及率
	public String getCoverageRate() {
		if(allCurEmployee != 0) {
			DecimalFormat f = new DecimalFormat();
			f.applyPattern("###.00%");
			return f.format(Double.valueOf(allDistinctEmployee).doubleValue()
					/ Long.valueOf(allCurEmployee).doubleValue()).toString();
		} else {
			return "000.00%";
		}
	}
	*/
}
