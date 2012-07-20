package com.isoftstone.agiledev.dao;

import org.apache.ibatis.session.SqlSession;

public interface SessionCreater {

	SqlSession openSession();
	void registerDomain(String alias,Class<?> c);
	void registerMapper(Class<?> c);
}
