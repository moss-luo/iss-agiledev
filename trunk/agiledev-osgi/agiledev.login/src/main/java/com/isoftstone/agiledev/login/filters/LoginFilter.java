package com.isoftstone.agiledev.login.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isoftstone.agiledev.domain.User;

public class LoginFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if(this.pass(request)){
			chain.doFilter(request, resp);
			return;
		}
		User user = (User) request.getSession().getAttribute("login_user");
		if(user!=null)
			chain.doFilter(request, resp);
		else{
			HttpServletResponse response = (HttpServletResponse) resp;
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.sendRedirect(request.getContextPath()+"/login.html");
		}
		
	}

	private boolean pass(HttpServletRequest request){
		String uri = request.getRequestURI();
		if(uri.contains(".jpg"))return true;
		else if(uri.contains(".png"))return true;
		else if(uri.contains(".gif"))return true;
		else if(uri.contains(".css"))return true;
		else if(uri.contains(".js"))return true;
		else if(uri.contains("login.html"))return true;
		else if(uri.contains("rand.action"))return true;
		else if(uri.contains("login"))return true;
		return false;
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
