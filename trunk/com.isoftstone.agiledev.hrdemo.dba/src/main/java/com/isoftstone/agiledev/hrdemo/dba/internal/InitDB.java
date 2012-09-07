package com.isoftstone.agiledev.hrdemo.dba.internal;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class InitDB {
	private JdbcTemplate jdbcTemplate;
	
	public void execute() {
		createTables();
		initData();
	}

	private void initData() {
		for (int i = 0; i < 30; i++) {
			jdbcTemplate.execute(String.format("INSERT INTO USER(name, password,mobile,email) VALUES('User_%s', 'User_%s','13888888888','User_%s@163.com')", i, i,i));
		}
	}

	private void createTables() {
		jdbcTemplate.execute("CREATE TABLE USER(id IDENTITY PRIMARY KEY, name CHAR(16), password CHAR(32), mobile CHAR(32), email CHAR(32))");
	}
	
	@Resource
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
}
