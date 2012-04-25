package com.isoftstone.agiledev.uploader;

import java.io.File;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.FileUploadInterceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 拦截通过struts上传的请求
 * 
 * @author sinner
 * 
 */
@SuppressWarnings("serial")
public class UploadInterceptor extends FileUploadInterceptor {

	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		File tempFile = null;
		try {
			

			HttpServletRequest request = (HttpServletRequest) ac.get(ServletActionContext.HTTP_REQUEST);
			StringBuffer sb = new StringBuffer();
			if (!(request instanceof MultiPartRequestWrapper)) {
				return invocation.invoke();
			}
			
			MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;

			Enumeration<String> fileParameterNames = multiWrapper
					.getFileParameterNames();

			while (fileParameterNames != null
					&& fileParameterNames.hasMoreElements()) {
				// get the value of this input tag
				String inputName = (String) fileParameterNames.nextElement();

				// get the content type
				String[] contentType = multiWrapper.getContentTypes(inputName);

				if (isNonEmpty(contentType)) {
					// get the name of the file from the input tag
					File[] files = multiWrapper.getFiles(inputName);
					if (files != null && files.length > 0) {
						for (int index = 0; index < files.length; index++) {
							sb.append(files[index].getPath());
						}
					}
				}
			}
			tempFile = new File(sb.toString());
			UploadHelper.upload(tempFile, ServletActionContext.getResponse(), request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean isNonEmpty(Object[] objArray) {
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++) {
			if (objArray[index] != null) {
				result = true;
			}
		}
		return result;
	}
//	/**
//	 * 下列setter提供给struts2参数注入，用于配置上传至远程ftp服务器和用户路径
//	 */

//	public void setRemoteIP(String ip) {
//		uploadConfig.setIp(ip);
//	}
//
//	public void setRemotePort(int port) {
//		uploadConfig.setPort(port);
//	}
//
//	public void setRemoteUser(String user) {
//		uploadConfig.setUser(user);
//	}
//
//	public void setRemotePassword(String pwd) {
//		uploadConfig.setPwd(pwd);
//	}
//
//	public void setMaxSleeping(int maxSleeping) {
//		uploadConfig.setMaxSleeping(maxSleeping);
//	}
//
//	public void setInitCapacity(int initCapacity) {
//		uploadConfig.setInitCapacity(initCapacity);
//	}
//	/**
//	 * 用户路径配置方式如下 header:/var/html/www/header;photo:/var/html/www/photo
//	 * 如上，配置了两个路径
//	 * ，各业务在上传时需传递"_r"参数指定上传的具体路径，如：_r=header表明要上传到/var/html/www/header/下
//	 * 
//	 * @param userDir
//	 */
//	public void setUserDir(String userDir) {
//		uploadConfig.setUserDir(userDir);
//	}
//
//	public void setReview(boolean isReview) {
//		uploadConfig.setReview(isReview);
//	}
//	public void setType(String type){
//		uploadConfig.setType(type);
//	}

//	private void putContextParam(UploadConfig uc){
//		try {
//			
//			UploadConfig c = (UploadConfig) ServletActionContext.getServletContext().getAttribute("uploadConfig");
//			if(c==null){
//				ServletActionContext.getServletContext().setAttribute("uploadConfig", uc);
//			}else{
//				c.setInitCapacity(uc.getInitCapacity());
//				c.setIp(uc.getIp());
//				c.setMaxSleeping(uc.getMaxSleeping());
//				c.setPort(uc.getPort());
//				c.setPwd(uc.getPwd());
//				c.setReview(uc.isReview());
//				c.setType(uc.getType());
//				c.setUser(uc.getUser());
//				c.setUserDir(uc.getUserDir());
//				ServletActionContext.getServletContext().setAttribute("uploadConfig", c);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 上传之前调用
//	 * @param beforeHandle
//	 */
//	public void setBeforeUploadHandle(String beforeHandleClasses) {
//		try {
//			if(beforeHandleClasses!=null){
//				String[] classes = beforeHandleClasses.split(";");
//				for (String string : classes) {
//					this.uploadConfig.addUploadBeforeHandle((UploadBeforeHandle)Class.forName(string).newInstance());
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	/**
//	 * 上传之后调用
//	 * @param afterHandle
//	 */
//	public void setAfterUploadHandle(String afterHandleClasses) {
//		try {
//			if(afterHandleClasses!=null){
//				String[] classes = afterHandleClasses.split(";");
//				for (String string : classes) {
//					this.uploadConfig.addUploadAfterHandle((UploadAfterHandle)Class.forName(string).newInstance());
//				}
//				
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
}
