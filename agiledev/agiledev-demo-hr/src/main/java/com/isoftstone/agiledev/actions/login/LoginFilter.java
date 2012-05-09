package com.isoftstone.agiledev.actions.login;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.isoftstone.agiledev.actions.system.user.User;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request2, ServletResponse response2,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) request2;
		if(this.pass(request)){
			chain.doFilter(request, response2);
			return;
		}
		User user = (User) request.getSession().getAttribute("login_user");
		if(user!=null)
			chain.doFilter(request, response2);
		else{
			HttpServletResponse response = (HttpServletResponse) response2;
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
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
