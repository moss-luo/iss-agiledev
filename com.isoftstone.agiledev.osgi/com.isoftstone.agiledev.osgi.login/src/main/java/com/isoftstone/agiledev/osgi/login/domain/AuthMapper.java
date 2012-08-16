package com.isoftstone.agiledev.osgi.login.domain;

import java.util.Map;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.isoftstone.agiledev.osgi.core.domain.EntityMapper;

@EntityMapper
public interface AuthMapper {

	@Select("select * from t_user where userid=#{loginName} and password=#{loginPassword}")
	@Results({
	      @Result(id = true, property = "id", column = "uid"),
	      @Result(property = "loginName", column = "userid"),
	      @Result(property = "loginPassword", column = "password")
	  })
	Auth login(Map<String, String> params);
}
