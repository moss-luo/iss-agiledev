package com.isoftstone.agiledev.hrdemo.dba.internal;

import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.springframework.stereotype.Component;

@Component
public class HSQLServer {
	private Server server;
	
	public void start() {
		server = new Server();
		HsqlProperties properties = new HsqlProperties();
		properties.setProperty("database.0", "mem:hrdemo");
		properties.setProperty("dbname.0", "hrdemo");
		server.setProperties(properties);
		server.setLogWriter(null);
		server.setErrWriter(null);
		
		server.start();
	}
	
	public void stop() {
		server.stop();
		server = null;
	}
}
