package com.isoftstone.agiledev.uploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

public class ReviewFilter implements Filter {

	private String contentType = "image/jpeg";
	
	public void destroy() {

	}
	@SuppressWarnings("deprecation")
	public void doFilter(ServletRequest req, ServletResponse res,FilterChain fc) throws IOException, ServletException {
		InputStream in = null;
		OutputStream out = null;
		try {
			String token = null;
			String isCode = req.getParameter("_c");
			if(Boolean.parseBoolean(isCode)==true){
				token = new String(Base64.decodeBase64(req.getParameter("_token").getBytes()));
			}else{
				token = req.getParameter("_token");
			}
			if (token != null) {
				
				if(req.getParameter("_type")!=null){

					HttpServletResponse response = (HttpServletResponse) res;
					response.setContentType(this.contentType);
					response.addHeader("Pragma", "no-chache");
					response.addHeader("Cache-Control", "no-chache");
					out = response.getOutputStream();
					
					String type = req.getParameter("_type");
					if(type.equals("remote")){
						
						HttpSession session = ((HttpServletRequest)req).getSession();
						UploadConfig config = (UploadConfig) session.getServletContext().getAttribute("uploadConfig");
						ResStoreUtil remoteDownloadder = new ResStoreUtil(config);
						byte[] bytes = remoteDownloadder.download(new String(token));
						out.write(bytes);
						return;
					}else if(type.equals("local")){
						in = new FileInputStream(new File(req.getRealPath("/")+"/"+new String(token)));
						byte[] outBuff = new byte[4096];
						int iSize;
						while (-1 != (iSize = in.read(outBuff))) {
							out.write(outBuff, 0, iSize);
						}
						out.flush();
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in!=null)in.close();
			if(out!=null)out.close();
		}
		
	}

	public void init(FilterConfig fc) throws ServletException {
		this.contentType = fc.getInitParameter("contentType")==null?this.contentType:fc.getInitParameter("contentType");
	}

}
