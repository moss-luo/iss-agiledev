package com.isoftstone.agiledev.uploader;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 过滤上传请求，输出文件的服务器相对路径.不执行具体的请求，直接返回
 * 
 * @author sinner
 * 
 */
public class UploadFilter implements Filter {



	public void destroy() {
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public void doFilter(ServletRequest request, ServletResponse res,
			FilterChain filterChain) throws IOException, ServletException {
		try {
			String contentType = request.getContentType();
			if (contentType == null)
				filterChain.doFilter(request, res);
			else if (contentType.contains("multipart/form-data")) {
				// 创建磁盘工厂，利用构造器实现内存数据储存量和临时储存路径
				DiskFileItemFactory factory = new DiskFileItemFactory();
				// 设置最多只允许在内存中存储的数据,单位:字节
				// factory.setSizeThreshold(4096);
				// 设置文件临时存储路径
				factory.setRepository(new File(request.getRealPath("/")+"/tmp"));
				// 产生一新的文件上传处理程式
				ServletFileUpload upload = new ServletFileUpload(factory);
				// 设置路径、文件名的字符集
				upload.setHeaderEncoding("UTF-8");
				// 设置允许用户上传文件大小,单位:字节
				upload.setSizeMax(1024 * 1024 * 100);
				// 解析请求，开始读取数据
				// Iterator<FileItem> iter = (Iterator<FileItem>)
				// upload.getItemIterator(request);
				// 得到所有的表单域，它们目前都被当作FileItem
				List<FileItem> fileItems = upload.parseRequest((HttpServletRequest) request);
				// 依次处理请求
				Iterator<FileItem> iter = fileItems.iterator();
				Map<String,String> formField = new Hashtable<String,String>();
				while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
					File tempFile = null;
					if (!item.isFormField()) {
						Field f = item.getClass().getDeclaredField("tempFile");
						boolean isAccessible = f.isAccessible();
						f.setAccessible(true);
						tempFile = (File) f.get(item);
						f.setAccessible(isAccessible);
					}else{
						formField.put(item.getFieldName(), item.getString());
					}
					if(tempFile!=null && tempFile.exists()){
						UploadHelper.upload(tempFile, (HttpServletResponse) res,new UploadServletReqeust(formField,request));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void init(FilterConfig fc) throws ServletException {
//
//		config = new UploadConfig();
//		config.setIp(fc.getInitParameter("remoteIP"));
//		config.setInitCapacity(Integer.parseInt(fc.getInitParameter("initCapacity")==null?"0":fc.getInitParameter("initCapacity")));
//		config.setMaxSleeping(Integer.parseInt(fc.getInitParameter("maxSleeping")==null?"0":fc.getInitParameter("maxSleeping")));
//		config.setPort(Integer.parseInt(fc.getInitParameter("remotePort")==null?"0":fc.getInitParameter("remotePort")));
//		config.setPwd(fc.getInitParameter("remotePassword"));
//		config.setUser(fc.getInitParameter("remoteUser"));
//		config.setReview(Boolean.parseBoolean(fc.getInitParameter("review")==null?"true":fc.getInitParameter("review")));
//		config.setUserDir(fc.getInitParameter("userDir"));
//		try {
//			if(fc.getInitParameter("uploadBeforeHandle")!=null){
//				config.addUploadBeforeHandle((UploadBeforeHandle)Class.forName(fc.getInitParameter("uploadBeforeHandle")).newInstance());
//			}
//			if(fc.getInitParameter("uploadAfterHandle")!=null){
//				config.addUploadAfterHandle((UploadAfterHandle)Class.forName(fc.getInitParameter("uploadAfterHandle")).newInstance());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ServletException("找不到uploadBeforeHandle对应的类");
//		} 
	}

}

