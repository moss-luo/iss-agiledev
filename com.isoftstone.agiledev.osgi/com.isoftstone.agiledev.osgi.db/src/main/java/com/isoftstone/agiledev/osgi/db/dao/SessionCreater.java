package com.isoftstone.agiledev.osgi.db.dao;
/**
 * Mybatis-Session创建接口,该接口暴露给外部，使得其他bundle可以注册domain和mapper给mybatis
 * @author david
 */
import org.apache.ibatis.session.SqlSession;

public interface SessionCreater {

	SqlSession openSession();
	void registerDomain(String alias,Class<?> c);
	void registerMapper(Class<?> c);
}
