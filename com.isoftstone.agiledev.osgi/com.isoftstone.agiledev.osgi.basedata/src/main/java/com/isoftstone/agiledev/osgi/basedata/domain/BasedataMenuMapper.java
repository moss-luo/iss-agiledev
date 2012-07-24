package com.isoftstone.agiledev.osgi.basedata.domain;

import java.util.List;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.isoftstone.agiledev.osgi.core.domain.EntityMapper;
import com.isoftstone.agiledev.osgi.core.web.navigate.Menu;

@EntityMapper
public interface BasedataMenuMapper {


	@Select("select * from t_permision where pid=#{parentId}")
	@Results({
		@Result(id=true,property="id",column="uid"),
		@Result(property="text",column="permisionName"),
		@Result(property="url",column="url"),
		@Result(property="parentId",column="pid"),
		@Result(property="hasChild",column="haschild")
	})
	List<Menu> queryMenu(String parentId);
	

	@Select("select * from t_permision where uid=4")
	@Results({
		@Result(id=true,property="id",column="uid"),
		@Result(property="text",column="permisionName"),
		@Result(property="url",column="url"),
		@Result(property="parentId",column="pid"),
		@Result(property="hasChild",column="haschild")
	})
	List<Menu> queryRootMenu();
}
