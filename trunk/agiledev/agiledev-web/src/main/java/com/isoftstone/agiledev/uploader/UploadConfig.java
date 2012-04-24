package com.isoftstone.agiledev.uploader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletRequest;

public class UploadConfig {

	private String ip;	//ftp服务器默认IP地址
	private int port;	//ftp服务默认端口
	private String user;
	private String pwd;
	private String type;
	private Integer maxSleeping = 8;
	private Integer initCapacity = 4;
	private String userDir = null;     //ftp用户目录
	private boolean isReview = false;	//是否提供预览(回写服务器路径)
	private List<UploadBeforeHandle> beforeHandles = new ArrayList<UploadBeforeHandle>();
	private List<UploadAfterHandle> afterHandles = new ArrayList<UploadAfterHandle>();
	
	
	private static Properties p=new Properties();
	static{
		URL url = UploadConfig.class.getResource("/agiledev/upload.properties");
		if(url!=null)
			try {
				p.load(url.openStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	


	/**
	 * 获取客户端传递的存在于配置中的远程上传路径对应的key
	 * 
	 * 用户路径配置方式如下 header:/var/html/www/header;photo:/var/html/www/photo
	 * 如上，配置了两个路径
	 * ，各业务在上传时需传递"_r"参数指定上传的具体路径，如：_r=header表明要上传到/var/html/www/header/下
	 * 
	 * @param request
	 * @return
	 */
	public String parseRemotePath(ServletRequest request) {
		if (this.getUserDir() == null)
			return null;
		if(!this.getUserDir().contains(":"))
			return this.getUserDir();
		String[] userDirs = this.getUserDir().split(";");
		for (String string : userDirs) {
			if (string.split(":")[0].equalsIgnoreCase(request.getParameter("_r")))
				return string.split(":")[1];
		}
		return null;
	}
	
	
	
	public String getIp() {
		return ip==null?p.getProperty("remoteIP"):ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port==0?Integer.parseInt(p.getProperty("remotePort")==null?"0":p.getProperty("remotePort").toString()):port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getUser() {
		return user==null?p.getProperty("remoteUser"):user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd==null?p.getProperty("remotePassword"):pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public Integer getMaxSleeping() {
		return maxSleeping==0?Integer.parseInt(p.getProperty("maxSleeping")==null?"8":p.getProperty("maxSleeping").toString()):maxSleeping;
	}
	public void setMaxSleeping(Integer maxSleeping) {
		this.maxSleeping = maxSleeping;
	}
	public Integer getInitCapacity() {
		return initCapacity==0?Integer.parseInt(p.getProperty("initCapacity")==null?"4":p.getProperty("initCapacity")):initCapacity;
	}
	public void setInitCapacity(Integer initCapacity) {
		this.initCapacity = initCapacity;
	}
	public String getUserDir() {
		return userDir==null?p.getProperty("userDir"):userDir;
	}
	public void setUserDir(String userDir) {
		this.userDir = userDir;
	}
	public boolean isReview() {
		return isReview || Boolean.parseBoolean(p.getProperty("review"));
	}
	public void setReview(boolean isReview) {
		this.isReview = isReview;
	}
	public List<UploadBeforeHandle> getBeforeHandles() throws Exception{
		if(this.beforeHandles.size()==0){
			String temp = p.getProperty("beforeUploadHandle");
			if(temp!=null){
				String[] beforeHandleClasses = temp.split(";");
				for (String str : beforeHandleClasses) {
					try {
						this.addUploadBeforeHandle((UploadBeforeHandle)Class.forName(str).newInstance());
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(str+"找不到");
					}
				}
			}
		}
		if(this.type!=null){
			this.beforeHandles.add(new BeforeUploadTypeCheckHandle());
		}
		
		return this.beforeHandles;
	}
	public List<UploadAfterHandle> getAfterHandles() throws Exception{
		if(this.afterHandles.size()==0){
			String temp = p.getProperty("afterUploadHandle");
			if(temp!=null){
				String[] beforeHandleClasses = temp.split(";");
				for (String str : beforeHandleClasses) {
					try {
						this.addUploadAfterHandle((UploadAfterHandle)Class.forName(str).newInstance());
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception(str+"找不到");
					}
				}
			}
		}
		return this.afterHandles;
	}
	
	public void addUploadBeforeHandle(UploadBeforeHandle handle){
		this.beforeHandles.add(handle);
	}
	public void addUploadAfterHandle(UploadAfterHandle handle){
		this.afterHandles.add(handle);
	}



	public String getType() {
		return type==null?p.getProperty("type"):type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
