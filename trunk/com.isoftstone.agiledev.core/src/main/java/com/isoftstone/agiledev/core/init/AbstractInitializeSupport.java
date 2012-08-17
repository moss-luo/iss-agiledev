package com.isoftstone.agiledev.core.init;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInitializeSupport implements InitializeAdaptor{

	protected List<InitField> initFields = new ArrayList<InitField>();
	
	protected InitField getField(String fieldName){
		for (InitField field :InitData.getInitData()) {
			if(field.getName().equals(fieldName))
				return field;
		}
		return null;
	}
	protected void addInitField(InitField initField){
//		this.initFields.add(initField);
		InitData.pushInitData(initField);
	}
}
