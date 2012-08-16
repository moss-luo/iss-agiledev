package com.isoftstone.agiledev.osgi.core.web.result;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.isoftstone.agiledev.osgi.core.web.Action;
import com.isoftstone.agiledev.osgi.core.web.ActionContext;

public class StreamResult implements Result {

	public static final String DEFAULT_PARAM = "inputName";

	protected String contentType = "text/plain";
	protected String contentLength;
	protected String contentCharSet;
	protected InputStream inputStream;
	protected int bufferSize = 1024;
	protected boolean allowCaching = true;

	protected InputStream getInputStream(Action action,Map<String,String> m){
		InputStream inputStream = null;
		try {
			for (String s: m.keySet()) {
				if(s.equals("inputStream")){
					String methodName = m.get(s);
					methodName = "get"+methodName.substring(0,1).toUpperCase()+methodName.substring(1);
					inputStream = (InputStream) action.getClass().getDeclaredMethod(methodName, null).invoke(action, null);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	
	protected String getContentType(Map<String,String> m){
		for (String s : m.keySet()) {
			if(s.equals("contentType"))
				return m.get(s);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(Action action, Object o) throws Exception {

		HttpServletResponse response = (HttpServletResponse) ActionContext
				.getResponse();
		OutputStream oOutput = null;
		try {
			
			inputStream = this.getInputStream(action, (Map<String, String>)o);
			
			contentType = this.getContentType((Map<String, String>)o);
			
			if(inputStream == null){
				throw new Exception("parameter inputStream must not be null!");
			}
			
			if (contentCharSet != null && !contentCharSet.equals("")) {
				response.setContentType(contentType + ";charset="+ contentCharSet);
			} else {
				response.setContentType(contentType);
			}

			if (!allowCaching) {
				response.addHeader("Pragma", "no-cache");
				response.addHeader("Cache-Control", "no-cache");
			}

			oOutput = response.getOutputStream();

			byte[] oBuff = new byte[bufferSize];
			int iSize;
			while (-1 != (iSize = inputStream.read(oBuff))) {
				oOutput.write(oBuff, 0, iSize);
			}
			oOutput.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
        finally {
            if (inputStream != null) inputStream.close();
            if (oOutput != null) oOutput.close();
        }

	}

}
