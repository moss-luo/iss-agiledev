package com.isoftstone.agiledev.actions.basedata.programs;

public class Programs {

	private int uid;
	
	private String name;//项目培训的名称
	
	private int trainId;//项目培训类别的id
	
	private String trainName;//项目培训类别的名称
	
	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTrainId() {
		return trainId;
	}

	public void setTrainId(int trainId) {
		this.trainId = trainId;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
}