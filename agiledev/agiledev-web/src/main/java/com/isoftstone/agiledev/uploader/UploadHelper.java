package com.isoftstone.agiledev.uploader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.isoftstone.agiledev.OperationResult;

public class UploadHelper {


	private static final String PARA1 = ".review?_token=";
	private static final String PARA2 = "&_type=";
	private static final Log log = LogFactory.getLog(UploadHelper.class);
	
	@SuppressWarnings("deprecation")
	public static void upload(File fileOnServer,HttpServletResponse response,ServletRequest request){
		try {
			/**
			 * 前置handle执行
			 */
			UploadConfig config = new UploadConfig();
			List<UploadBeforeHandle> beforeHandles = config.getBeforeHandles();
			for (UploadBeforeHandle handle : beforeHandles) {
				OperationResult result = handle.handle(fileOnServer, config, request);
				if(!result.isSuccess()){
					printResult(result.getMsg(), response);
					return;
				}
			}
			String uniqueId = String.valueOf(System.currentTimeMillis());
			String userDir = config.parseRemotePath(request);
			String fileFinalPath = null;
			if (config.getIp() == null) { //上传到本地服务器
				if(userDir == null){
					log.info("userDir is null,file will be uploaded temp folder");
					userDir = "/tmp";
				}
				File temp = new File(request.getRealPath("/")+"/"+userDir);
				if (!temp.exists())
					temp.mkdir();
				String uploadFilePath = temp + "/" + uniqueId;
				
				FileInputStream fis = new FileInputStream(fileOnServer);
				FileOutputStream fos = new FileOutputStream(uploadFilePath);
				byte[] buffer = new byte[1024];
				int length = fis.read(buffer);
				while (length != -1) {
					fos.write(buffer);
					length = fis.read(buffer);
				}
				fos.flush();
				fos.close();
				fis.close();
				fileOnServer.delete();
				
				
				if (config.isReview()) {
					byte[] t = Base64.encodeBase64((userDir.substring(1) + "/" + uniqueId).getBytes());
					String filePath = PARA1 + new String(t);
					fileFinalPath = System.currentTimeMillis()+filePath+PARA2+"local";
	
				}
			}else{// 上传到远程ftp服务器
				
				
				ResStoreUtil remoteUploader = new ResStoreUtil(config);

				if(userDir == null){
					log.error("userDir can't be null");
					throw new Exception("userDir can't be null");
				}
				String remotePath = userDir + "/" + uniqueId;
				
//				boolean status = remoteUploader.uploadFile(userDir, uniqueId, new FileInputStream(fileOnServer));//.upload(fileOnServer.getPath(),remotePath);
				boolean status = remoteUploader.uploadFile(remotePath, fileOnServer);
				if (status) {
					fileOnServer.delete();
				}
				if (config.isReview()) {
					
					remotePath = userDir + "/" + uniqueId;
					String reviewPath = PARA1 + new String(Base64.encodeBase64(remotePath.getBytes()));
					fileFinalPath = System.currentTimeMillis()+reviewPath+PARA2+"remote&_c=true";
				}
			}
			
			List<UploadAfterHandle> afterHandles = config.getAfterHandles();
			for (UploadAfterHandle handle : afterHandles) {
				handle.handle(uniqueId, config, request);
			}
			printResult(fileFinalPath,response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 输出消息
	 * @param 消息
	 * @param contentType 
	 * @throws IOException
	 */
	private static void printResult(String msg,HttpServletResponse response) throws IOException{
		response.setContentType("text/html;charset=UTF-8");
		response.addHeader("Content-Type", "text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter outter = response.getWriter();
		outter.print(msg);
		outter.flush();
		outter.close();
	}
}
