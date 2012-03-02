package com.isoftstone.agiledev.uploader;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPoolFactory;


public class PoolableObjectFactorySample implements PoolableObjectFactory {
	
	private static String ip;	//ftp服务器默认IP地址
	private static int port;	//ftp服务默认端口
	private static String user;
	private static String pwd;
	public static Integer maxSleeping = 8;
	public static Integer initCapacity = 4;
	private static String userDir = null;     //ftp用户目录

	public PoolableObjectFactorySample(){
	}
	public PoolableObjectFactorySample(UploadConfig uploadConfig){
		ip = uploadConfig.getIp();
		port = uploadConfig.getPort();
		user = uploadConfig.getUser();
		pwd = uploadConfig.getPwd();
		maxSleeping = uploadConfig.getMaxSleeping();
		initCapacity = uploadConfig.getInitCapacity();
	}
	
    public Object makeObject() throws Exception {
    	try {
    		FTPClient ftpClient = new FTPClient();
    		ftpClient.connect(ip, port);
    		ftpClient.setControlEncoding("GBK");
    		if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
    			ftpClient.login(user, pwd);
    		}
    		if(userDir==null){
    			userDir = ftpClient.printWorkingDirectory();
    		}
			return ftpClient;
		} catch (Exception e) {
			System.out.println("与主机:"+ip+":"+port+"连接失败!");
			throw e;
		}
    }
    
    public void activateObject(Object obj) throws Exception {
    	
    	FTPClient ftpClient = (FTPClient)obj;
    	try {
    		if(userDir!=null){
    			ftpClient.changeWorkingDirectory(userDir);
    		}
    		ftpClient.getStatus();
		} catch (Exception e) {
			//ftpClient连接失效则重新登录
			if(ftpClient.getReplyCode()!=FTPReply.SERVICE_NOT_AVAILABLE){
				e.printStackTrace();
			}
			ftpClient.connect(ip, port);
			ftpClient.setControlEncoding("GBK");
			ftpClient.login(user, pwd);
			if(userDir!=null){
    			ftpClient.changeWorkingDirectory(userDir);
    		}
		}
    }
    
    public void passivateObject(Object obj) throws Exception {
        //System.err.println("Passivating Object " + obj);
    }
    
    public boolean validateObject(Object obj) {
        return true;
    }
    
    public void destroyObject(Object obj) throws Exception {
//    	System.out.println("Destroying Object " + obj);
    	FTPClient ftpClient = (FTPClient)obj;
    	/* 断开与远程服务器的连接 */
    	if(ftpClient.isConnected()) { 
			ftpClient.disconnect();
		}
    }
    
    public static void main(String[] args) {
    	FTPClient obj = null;
        PoolableObjectFactory factory = new PoolableObjectFactorySample();
        ObjectPoolFactory poolFactory = new StackObjectPoolFactory(factory);
        ObjectPool pool = poolFactory.createPool();
        try {
            for(long i = 0; i < 100 ; i++) {
                System.out.println("== " + i + " ==");
                obj = (FTPClient)pool.borrowObject();
                
                System.out.println(obj.printWorkingDirectory());
                
                pool.returnObject(obj);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try{
                if (obj != null) {
                    pool.returnObject(obj);
                }
                pool.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
