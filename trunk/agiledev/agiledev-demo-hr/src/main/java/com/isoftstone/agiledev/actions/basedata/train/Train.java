package com.isoftstone.agiledev.actions.basedata.train;


public class Train {

	private String uid;
	private String trainName;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getTrainName() {
		return trainName;
	}
	//@StringLengthFieldValidator(message="培训类别必须在1-10之间",minLength="1",maxLength="10")
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	
}
