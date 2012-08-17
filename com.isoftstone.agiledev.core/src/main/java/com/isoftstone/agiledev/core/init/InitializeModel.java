package com.isoftstone.agiledev.core.init;

public class InitializeModel {

	private Class<?>[] initialiedType;

	public InitializeModel(Class<?>[] initialiedType) {
		super();
		this.initialiedType = initialiedType;
	}

	public Class<?>[] getInitialiedType() {
		return initialiedType;
	}
	
}
