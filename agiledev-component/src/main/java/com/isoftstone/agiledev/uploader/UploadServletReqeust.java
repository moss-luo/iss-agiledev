package com.isoftstone.agiledev.uploader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * commons-fileupload组件上传后，filter拿不到表单提交的其他参数，所以自定义一个ServletRequest实现类，只封装了
 * getParameter(String name);getParametersMap();getSession();getRealPath(String path)
 * @author sinner
 *
 */
public class UploadServletReqeust implements ServletRequest {

	Map<String,String> param = null;
	ServletRequest request = null; 
	UploadServletReqeust(Map<String,String> p,ServletRequest request){
		this.param = p;
		this.request = request;
	}
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getContentLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getLocalName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocalPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getLocales() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getParameter(String arg0) {
		return this.param.get(arg0);
	}

	@SuppressWarnings("rawtypes")
	public Map getParameterMap() {
		return this.param;
	}

	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public String[] getParameterValues(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProtocol() {
		// TODO Auto-generated method stub
		return null;
	}

	public BufferedReader getReader() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("deprecation")
	public String getRealPath(String arg0) {
		return this.request.getRealPath(arg0);
	}

	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getRemotePort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getScheme() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerName() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isSecure() {
		// TODO Auto-generated method stub
		return false;
	}

	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

	public void setCharacterEncoding(String arg0)
			throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
	}
	public String getAuthType() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}
	public Cookie[] getCookies() {
		// TODO Auto-generated method stub
		return null;
	}
	public long getDateHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getHeader(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Enumeration getHeaders(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public int getIntHeader(String arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getMethod() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getPathInfo() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getPathTranslated() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getQueryString() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getRemoteUser() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getRequestURI() {
		// TODO Auto-generated method stub
		return null;
	}
	public StringBuffer getRequestURL() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getRequestedSessionId() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getServletPath() {
		// TODO Auto-generated method stub
		return null;
	}
	public HttpSession getSession() {
		return ((HttpServletRequest)request).getSession();
	}
	public HttpSession getSession(boolean arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	public Principal getUserPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean isRequestedSessionIdFromCookie() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isRequestedSessionIdFromURL() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isRequestedSessionIdFromUrl() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isRequestedSessionIdValid() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isUserInRole(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
