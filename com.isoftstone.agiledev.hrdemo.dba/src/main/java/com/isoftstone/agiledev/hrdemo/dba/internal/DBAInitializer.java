package com.isoftstone.agiledev.hrdemo.dba.internal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class DBAInitializer {
	private HSQLServer server;
	
	@Resource
	private InitDB initDB;
	
	@PreDestroy
	public void stop() {
		server.stop();
	}

	@PostConstruct
	public void start() {
		server = new HSQLServer();
		server.start();
		
		initDB.execute();
	}
}
