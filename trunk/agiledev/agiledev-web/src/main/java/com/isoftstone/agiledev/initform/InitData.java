package com.isoftstone.agiledev.initform;

import java.util.ArrayList;
import java.util.List;

public class InitData {


	private static List<InitField> initFields = new ArrayList<InitField>();
	public static void pushInitData(InitField initField){
		initFields.add(initField);
	}
	public static List<InitField> getInitData(){
		return initFields;
	}
	public static void clear(){
		initFields.clear();
	}
}
