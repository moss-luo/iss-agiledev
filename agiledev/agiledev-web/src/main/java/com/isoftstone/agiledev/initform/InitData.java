package com.isoftstone.agiledev.initform;

import java.util.ArrayList;
import java.util.List;
/**
 * 辅助类，用于在各result的实现中持有全局的初始化信息对象
 * @author sinner
 *
 */
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
