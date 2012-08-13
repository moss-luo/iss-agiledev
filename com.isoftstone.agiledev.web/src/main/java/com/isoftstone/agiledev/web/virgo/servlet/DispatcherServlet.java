package com.isoftstone.agiledev.web.virgo.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

@SuppressWarnings("serial")
public class DispatcherServlet extends org.springframework.web.servlet.DispatcherServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		String contextClass = config.getInitParameter("contextClass");
		if (contextClass != null) {
			try {
				setContextClass(Class.forName(contextClass));
			} catch (ClassNotFoundException e) {
				throw new ServletException(String.format("Context class %s not found", contextClass), e);
			}
		}
		
		super.init(config);
	}
}
