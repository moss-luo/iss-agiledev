package com.isoftstone.agiledev.uploader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.ObjectPoolFactory;
import org.apache.commons.pool.PoolableObjectFactory;
import org.apache.commons.pool.impl.StackObjectPoolFactory;

/**
 * 资源存储接口实现类
 * 
 * @author zhangjie
 */
public class ResStoreUtil {

	@SuppressWarnings("unused")
	private static Properties p = null;
	/** 操作系统名字 */
	private static String systemName = "LINUX";
	/** linux下用户目录 */
	private static String userHome = "";

	private FTPClient ftpClient;

	private PoolableObjectFactory factory = null;// new
													// PoolableObjectFactorySample();

	private ObjectPoolFactory poolFactory = null;// new
													// StackObjectPoolFactory(factory,PoolableObjectFactorySample.maxSleeping,PoolableObjectFactorySample.initCapacity);

	private ObjectPool pool = null;// poolFactory.createPool();

	/**
	 * 读取配置文件
	 */
	static {
		systemName = System.getProperty("os.name").toUpperCase();
		// 业务中已经加上了
		// userHome =
		// ModuleConfig.getItemValue("appConf","access.ResServer.Home");
	}

	/**
	 * 构造函数，获取对象池中的ftpClient对象
	 */
	public ResStoreUtil() {
		// init(null);
	}

	public ResStoreUtil(UploadConfig uploadConfig) {
		init(uploadConfig);
	}

	private void init(UploadConfig uploadConfig) {
		if (uploadConfig != null)
			factory = new PoolableObjectFactorySample(uploadConfig);
		else
			factory = new PoolableObjectFactorySample();
		poolFactory = new StackObjectPoolFactory(factory,
				PoolableObjectFactorySample.maxSleeping,
				PoolableObjectFactorySample.initCapacity);
		pool = poolFactory.createPool();
		try {
			ftpClient = (FTPClient) pool.borrowObject();// 象池中借出对象
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Description: 向FTP服务器上传文件
	 * 
	 * @param path
	 *            FTP服务器保存目录
	 * @param filename
	 *            上传到FTP服务器上的文件名
	 * @param input
	 *            输入流
	 * @return 成功返回true，否则返回false
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws NoSuchElementException
	 */
	public boolean uploadFile(String path, String filename, InputStream input) {
		// 初始表示上传失败
		boolean success = false;
		ftpClient = new FTPClient();
		try {
			int reply;
			// 连接FTP服务器
			// 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
			ftpClient.connect("10.36.128.189", 21);
			// 登录ftp
			ftpClient.login("test", "test");
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return success;
			}

			// 转到指定上传目录
			ftpClient.setControlEncoding("utf-8");
			ftpClient.changeWorkingDirectory(path);
			// 设置二进制传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 将上传文件存储到指定目录
			ftpClient.storeFile(path + "/" + filename, input);
			// 关闭输入流
			input.close();
			// 退出ftp
			ftpClient.logout();
			// 表示上传成功
			success = true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// if (ftpClient.isConnected()) {
			// try {
			// //ftpClient.disconnect();
			// } catch (IOException ioe) {
			// }
			// }
		}
		return success;
	}

	public boolean uploadFile(String remote, File local)
			throws NoSuchElementException, IllegalStateException, Exception {
		return this.uploadFile(remote, local, 0) == UploadStatus.Upload_New_File_Success;
	}

	public static void main(String[] args) {
		UploadConfig c = new UploadConfig();
		c.setIp("10.36.128.189");
		c.setUser("test");
		c.setPort(21);
		c.setPwd("test");
		ResStoreUtil r = new ResStoreUtil(c);

		try {
			boolean b = r.uploadFile("agileupload", "12345.jpg",
					new FileInputStream(new File("/media/E/happy/pic/1.JPG")));
			System.out.println(b);
			// System.out.println(r.uploadFile("upload/12345.jpg", new File(
			// "/media/E/happy/pic/1.JPG"), 0));
			// System.out.println(b);
			r.download("upload/12345.jpg", "/home/sinner/123.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从FTP服务器上向本地下载文件,支持断点续传
	 * 
	 * @param remote
	 *            远程文件路径
	 * @param local
	 *            本地文件路径
	 * @throws Exception
	 */
	public void download(String remote, String local) throws Exception {
		// if (systemName.indexOf("LINUX") != -1) {
		// remote = userHome + remote;
		// File file = new File(remote);
		// boolean result = false;
		// if (file.exists()) {
		// result = uploadFile(file, local);
		// if (false == result) {
		// throw new Exception("文件下载失败");
		// }
		// } else {
		// throw new Exception(String.format(
		// "远程文件不存在 remote filepath : %s", remote));
		// }
		// } else {
		// ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象

		// 转到指定上传目录
		ftpClient.setControlEncoding("utf-8");
		String workingDir = ftpClient.printWorkingDirectory();
		try {
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检查远程文件是否存在
			FTPFile[] files = ftpClient.listFiles(new String(remote
					.getBytes("GBK"), "iso-8859-1"));

			if (files.length != 1) {
				throw new Exception("远程文件不存在 remotePath = " + remote);
			}
			@SuppressWarnings("unused")
			long lRemoteSize = files[0].getSize();
			File f = new File(local);
			// 本地存在文件，进行断点下载
			if (f.exists()) {
				@SuppressWarnings("unused")
				long localSize = f.length();
				// 判断本地文件大小是否大于远程文件大小
				// if(localSize >= lRemoteSize) {
				// throw new Exception("本地文件已存在，下载中止 localPath = " + local +
				// ", remotePath = " + remote);
				// }
				// 进行断点续传，并记录状态
				FileOutputStream out = new FileOutputStream(f, false);
				// ftpClient.setRestartOffset(localSize);
				InputStream in = ftpClient.retrieveFileStream(new String(remote
						.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
				}
				in.close();
				out.close();
			} else {
				OutputStream out = new FileOutputStream(f);
				InputStream in = ftpClient.retrieveFileStream(new String(remote
						.getBytes("GBK"), "iso-8859-1"));
				byte[] bytes = new byte[1024];
				int c;
				while ((c = in.read(bytes)) != -1) {
					out.write(bytes, 0, c);
				}
				in.close();
				out.close();
			}
			ftpClient.completePendingCommand();
		} finally {
			if (ftpClient != null && workingDir != null) {
				ftpClient.changeWorkingDirectory(workingDir);
				// pool.returnObject(ftpClient);
			}
		}
		// }
	}

	/**
	 * 从FTP服务器上下载文件
	 * 
	 * @param remote
	 *            远程文件路径
	 * @throws Exception
	 */
	public byte[] download(String remote) throws Exception {
		// if (systemName.indexOf("LINUX") != -1) {
		// remote = userHome + remote;
		// File file = new File(remote);
		// byte data[] = new byte[2048];
		//
		// if (file.exists()) {
		// FileInputStream fis = new FileInputStream(file);
		// ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// DataOutputStream out = new DataOutputStream(baos);
		// int count;
		// while ((count = fis.read(data)) > 0) {
		// out.write(data, 0, count);
		// }
		// out.flush();
		// out.close();
		// fis.close();
		// return baos.toByteArray();
		// } else {
		// throw new Exception(String.format(
		// "远程文件不存在 remote filepath : %s", remote));
		// }
		// } else {
		ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象
		String workingDir = ftpClient.printWorkingDirectory();
		try {
			// 设置被动模式
			ftpClient.enterLocalPassiveMode();
			// 设置以二进制方式传输
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检查远程文件是否存在
			FTPFile[] files = ftpClient.listFiles(new String(remote
					.getBytes("GBK"), "iso-8859-1"));

			if (files.length != 1) {
				throw new Exception("远程文件不存在 remotePath = " + remote);
			}
			// 本地存在文件，进行断点下载
			InputStream in = ftpClient.retrieveFileStream(new String(remote
					.getBytes("GBK"), "iso-8859-1"));

			byte[] buffer = new byte[1024];
			int count = 0;
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);

			while ((count = in.read(buffer)) > 0) {
				out.write(buffer, 0, count);
			}
			out.close();
			in.close();

			ftpClient.completePendingCommand();
			return baos.toByteArray();
		} finally {
			if (ftpClient != null && workingDir != null) {
				ftpClient.changeWorkingDirectory(workingDir);
				pool.returnObject(ftpClient);
			}
		}
		// }
	}

	/**
	 * 从本地上传文件到FTP服务器，（剪切）
	 * 
	 * @param local
	 *            本地文件名称，绝对路径
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @param isSupplement
	 *            是否需要追加文件
	 * @throws Exception
	 */
	public UploadStatus upload(String local, String remote) throws Exception {
		if (systemName.indexOf("LINUX") != -1) {
			remote = userHome + remote;
			File file = new File(local);
			if (file.exists()) {
				// 检查本地目录是否存在
				boolean mkdir = CreateDirecroty(remote);
				if (mkdir == false) {
					throw new Exception("远程目录创建失败");
				}
				UploadStatus status = this.uploadFile(remote, file, 0);
				return status;
			} else {
				throw new Exception(String.format(
						"本地文件不存在 local filepath : %s", local));
			}
		} else {
			String workingDir = ftpClient.printWorkingDirectory();
			try {
				// 设置PassiveMode传输
				ftpClient.enterLocalPassiveMode();
				// 设置以二进制流的方式传输
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.setControlEncoding("GBK");
				UploadStatus result = null;
				// 对远程目录的处理
				String remoteFileName = remote;
				if (remote.contains("/")) {
					remoteFileName = remote
							.substring(remote.lastIndexOf("/") + 1);
					if (null != remoteFileName
							&& remoteFileName.indexOf("\\") == 0) {
						remoteFileName = remoteFileName.substring(1,
								remoteFileName.length());
					}
					// 创建服务器远程目录结构，创建失败直接返回
					if (CreateDirecroty2(remote) == UploadStatus.Create_Directory_Fail) {
						throw new Exception("创建服务器远程目录结构失败 remotePath = "
								+ remote);
					}
				}
				// 检查远程是否存在文件
				FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
						.getBytes("GBK"), "iso-8859-1"));
				if (files.length == 1) {
					long remoteSize = files[0].getSize();
					File f = new File(local);
					long localSize = f.length();
					if (remoteSize == localSize || remoteSize > localSize) {
						System.out.println("远程目录已存在文件 remotePath = " + remote);
						return result;
					}
					// 尝试移动文件内读取指针,实现断点续传
					result = uploadFile(remoteFileName, f, remoteSize);
					// 如果断点续传没有成功，则删除服务器上文件，重新上传
					if (result == UploadStatus.Upload_From_Break_Failed) {
						if (!ftpClient.deleteFile(remoteFileName)) {
							System.out
									.println("断点续传没有成功，删除服务器上文件失败 remotePath = "
											+ remote);
							return result;
						}
						result = uploadFile(remoteFileName, f, 0);
					}
				} else {
					result = uploadFile(remoteFileName, new File(local), 0);
				}
				return result;
				// 打印上传结果 result
			} finally {
				if (ftpClient != null && workingDir != null) {
					ftpClient.changeWorkingDirectory(workingDir);
					pool.returnObject(ftpClient);
				}
			}
		}
	}

	/**
	 * 上传文件到FTP服务器
	 * 
	 * @param local_in
	 *            文件输入流
	 * @param remote
	 *            远程文件路径，使用/home/directory1/subdirectory/file.ext或是
	 *            http://www.guihua.org /subdirectory/file.ext
	 *            按照Linux上的路径指定方式，支持多级目录嵌套，支持递归创建不存在的目录结构
	 * @param isSupplement
	 *            是否需要追加文件
	 * @throws Exception
	 */
	public void upload(byte[] parm_bytes, String remote, boolean isSupplement)
			throws Exception {
		if (systemName.indexOf("LINUX") != -1) {
			remote = userHome + remote;
			File file = new File(remote);
			if (null == parm_bytes || parm_bytes.length <= 0) {
				throw new Exception(String.format("上传的数据为空"));
			}

			// 检查本地目录是否存在
			boolean mkdir = CreateDirecroty(remote);
			if (mkdir == false) {
				throw new Exception("远程目录创建失败");
			}

			InputStream input = new ByteArrayInputStream(parm_bytes);
			if (!isSupplement) {
				OutputStream out = new FileOutputStream(file);
				byte[] bytes = new byte[1024];
				int c;
				while ((c = input.read(bytes)) != -1) {
					out.write(bytes, 0, c);
				}
				out.flush();
				out.close();
				input.close();
			} else {
				RandomAccessFile out = new RandomAccessFile(file, "rw");
				out.seek(out.length());
				byte[] bytes = new byte[1024];
				int c;
				while ((c = input.read(bytes)) != -1) {
					out.write(bytes, 0, c);
				}
				out.close();
				input.close();
			}
		} else {
			ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象
			String workingDir = ftpClient.printWorkingDirectory();
			try {
				// 设置PassiveMode传输
				ftpClient.enterLocalPassiveMode();
				// 设置以二进制流的方式传输
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.setControlEncoding("GBK");
				@SuppressWarnings("unused")
				UploadStatus result;
				// 对远程目录的处理
				String remoteFileName = remote;
				@SuppressWarnings("unused")
				String path = remote;
				if (remote.contains("/")) {
					remoteFileName = remote
							.substring(remote.lastIndexOf("/") + 1);
					path = remote.substring(0, remote.lastIndexOf("/") + 1);
					// 创建服务器远程目录结构，创建失败直接返回
					if (CreateDirecroty2(remote) == UploadStatus.Create_Directory_Fail) {
						throw new Exception("创建服务器远程目录结构失败 remotePath = "
								+ remote);
					}
				}
				// InputStream input = new StringBufferInputStream(new
				// String(parm_bytes));
				InputStream input = new ByteArrayInputStream(parm_bytes);
				// 检查远程是否存在文件
				String fullName = workingDir + "/" + remote;
				FTPFile[] files = ftpClient.listFiles(new String(remoteFileName
						.getBytes("GBK"), "iso-8859-1"));
				if (files.length == 1 && isSupplement) {
					OutputStream out = null;
					try {
						out = ftpClient.appendFileStream(new String(fullName
								.getBytes("GBK"), "iso-8859-1"));
						byte[] bytes = new byte[1024];
						int c;
						while ((c = input.read(bytes)) != -1) {
							out.write(bytes, 0, c);
						}
						out.flush();
						out.close();
						input.close();
						result = UploadStatus.Upload_New_File_Success;
					} catch (Exception e) {
						if (out != null) {
							try {
								out.close();
							} catch (Exception e1) {
								// do nothing;
							}
						}
						result = UploadStatus.Upload_New_File_Failed;
					}
					ftpClient.completePendingCommand();
				} else {
					try {
						ftpClient.storeFile(new String(
								fullName.getBytes("GBK"), "iso-8859-1"), input);
						// ftpClient.storeUniqueFile(remoteFileName, local);

						input.close();
						result = UploadStatus.Upload_New_File_Success;
					} catch (Exception e) {
						if (input != null) {
							try {
								input.close();
							} catch (Exception e1) {
								// do nothing;
							}
						}
						result = UploadStatus.Upload_New_File_Failed;
					}
				}
				// 打印上传结果 result
			} finally {
				if (ftpClient != null && workingDir != null) {
					ftpClient.changeWorkingDirectory(workingDir);
					pool.returnObject(ftpClient);
				}
			}
		}
	}

	/**
	 * 上传文件到服务器,新上传和断点续传
	 * 
	 * @param remoteFile
	 *            远程文件名，在上传之前已经将服务器工作目录做了改变
	 * @param localFile
	 *            本地文件 File句柄，绝对路径
	 * @param isSupplement
	 *            是否需要追加文件
	 * @return
	 * @throws Exception
	 * @throws IllegalStateException
	 * @throws NoSuchElementException
	 */
	private UploadStatus uploadFile(String remoteFile, File localFile,
			long remoteSize) throws NoSuchElementException,
			IllegalStateException, Exception {
		UploadStatus status;
		// 显示进度的上传
		int base = 100; // 基数
		while (localFile.length() < base && base > 0) {
			base = base / 10;
		}
		if (base <= 0)
			base = 1;
		long step = localFile.length() / base;
		long process = 0;
		long localreadbytes = 0L;
		RandomAccessFile raf = new RandomAccessFile(localFile, "r");
		// ftpClient = new FTPClient();
		// int reply;
		// // 连接FTP服务器
		// // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
		// ftpClient.connect(uploadConfig.getIp(), uploadConfig.getPort());
		// ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		// // 登录ftp
		// ftpClient.login(uploadConfig.getUser(), uploadConfig.getPwd());
		// reply = ftpClient.getReplyCode();
		// if (!FTPReply.isPositiveCompletion(reply)) {
		// ftpClient.disconnect();
		// }

		ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

		OutputStream out = ftpClient.appendFileStream(new String(remoteFile
				.getBytes("GBK"), "iso-8859-1"));
		if (out == null) {
			String message = ftpClient.getReplyString();
			System.out.println(message);
			throw new RuntimeException(message);
		}
		// 断点续传
		if (remoteSize > 0) {
			ftpClient.setRestartOffset(remoteSize);
			process = remoteSize / step;
			raf.seek(remoteSize);
			localreadbytes = remoteSize;
		}
		byte[] bytes = new byte[1024];
		int c;
		while ((c = raf.read(bytes)) != -1) {
			out.write(bytes, 0, c);
			localreadbytes += c;
			if (localreadbytes / step != process) {
				process = localreadbytes / step;
				System.out.println("上传进度:" + process * (100 / base));
				// TODO 汇报上传状态
			}
		}
		out.flush();
		raf.close();
		out.close();
		boolean result = ftpClient.completePendingCommand();
		if (remoteSize > 0) {
			status = result ? UploadStatus.Upload_From_Break_Success
					: UploadStatus.Upload_From_Break_Failed;
		} else {
			status = result ? UploadStatus.Upload_New_File_Success
					: UploadStatus.Upload_New_File_Failed;
		}
		return status;
	}

	/**
	 * 递归创建服务器目录
	 * 
	 * @param remote
	 *            服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public boolean CreateDirecroty(String remote) throws IOException {
		if (remote.indexOf("/") == -1) {
			System.out.println("文件存储至用户目录下");
			return true;
		} else {
			remote = remote.substring(0, remote.lastIndexOf("/") + 1);
			File dirs = new File(remote);
			if (!dirs.exists()) {
				if (!dirs.isDirectory()) {
					dirs.mkdirs();
				}
			}
			return true;
		}
	}

	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote
	 *            远程服务器文件绝对路径
	 * @param ftpClient
	 *            FTPClient 对象
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public UploadStatus CreateDirecroty2(String remote) throws IOException {
		UploadStatus status = UploadStatus.Create_Directory_Success;
		String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		if (!directory.equalsIgnoreCase("/")
				&& !ftpClient.changeWorkingDirectory(new String(directory
						.getBytes("GBK"), "iso-8859-1"))) {
			// 如果远程目录不存在，则递归创建远程服务器目录
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			while (true) {
				String subDirectory = new String(remote.substring(start, end)
						.getBytes("GBK"), "iso-8859-1");
				if (!ftpClient.changeWorkingDirectory(subDirectory)) {
					if (ftpClient.makeDirectory(subDirectory)) {
						ftpClient.changeWorkingDirectory(subDirectory);
					} else {
						System.out.println("创建目录失败");
						return UploadStatus.Create_Directory_Fail;
					}
				}
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return status;
	}

	/**
	 * 删除远程服务器文件
	 * 
	 * @param remote
	 *            远程服务器文件路径
	 * @throws Exception
	 */
	public void delete(String remote) throws Exception {
		if (systemName.indexOf("LINUX") != -1) {
			remote = userHome + remote;
			File file = new File(remote);
			if (file.exists()) {
				file.delete();
			}
		} else {
			ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象
			String workingDir = ftpClient.printWorkingDirectory();
			try {
				remote = workingDir + "/"
						+ new String(remote.getBytes("GBK"), "iso-8859-1");
				File file = new File(remote);
				if (file.isDirectory()) {
					// 删除ftp上指定文件
					ftpClient.removeDirectory(remote);
				} else {
					// 删除ftp上指定文件
					ftpClient.deleteFile(remote);
				}
			} finally {
				if (ftpClient != null && workingDir != null) {
					ftpClient.changeWorkingDirectory(workingDir);
					pool.returnObject(ftpClient);
				}
			}
		}
	}

	/**
	 * 移动远程服务器文件（剪切）
	 * 
	 * @param from
	 *            远程服务器文件移动前的路径
	 * @param to
	 *            远程服务器文件移动后的路径
	 * @throws Exception
	 */
	public void move(String from, String to) throws Exception {
		if (systemName.indexOf("LINUX") != -1) {
			String sep = File.separator;
			from = userHome + from;
			to = userHome + to;
			File file = new File(from);
			from = from.replaceAll("//", sep);
			to = to.replaceAll("//", sep);

			if (file.exists()) {
				// 检查远程目录是否存在
				boolean mkdir = CreateDirecroty(to);
				if (mkdir == false) {
					throw new Exception("远程目录创建失败");
				}

				String[] cmd = null;
				if (systemName.indexOf("WINDOWS") != -1) {
					cmd = new String[] { "cmd", "/c", "move", from, to };
				} else if (systemName.indexOf("LINUX") != -1) {
					cmd = new String[] { "mv", from, to };
				} else {
					throw new Exception("不支持本操作系统 os: " + systemName);
				}

				Process pos = Runtime.getRuntime().exec(cmd);
				if (pos.waitFor() != 0) {
					if (pos.exitValue() == 1) // p.exitValue()==0表示正常结束，1：非正常结束
						throw new Exception("命令执行失败!");
				}

				// file.renameTo(new File(to));
			} else {
				throw new Exception(String.format(
						"需要移动的文件不存在 from filepath : %s", from));
			}
		} else {
			ftpClient = (FTPClient) pool.borrowObject(); // 从对象池中借出对象
			String workingDir = ftpClient.printWorkingDirectory();
			try {
				from = new String(from.getBytes("GBK"), "iso-8859-1");
				to = new String(to.getBytes("GBK"), "iso-8859-1");
				// 对远程目录的处理
				if (to.contains("/")) {
					// 创建服务器远程目录结构，创建失败直接返回
					if (CreateDirecroty2(to) == UploadStatus.Create_Directory_Fail) {
						throw new Exception("创建服务器远程目录结构失败 remotePath = " + to);
					}
				}
				ftpClient
						.rename(workingDir + "/" + from, workingDir + "/" + to);
				// 删除ftp上指定文件
				// 检查远程是否存在文件
				FTPFile[] files = ftpClient.listFiles(from);
				if (files.length == 1) {
					ftpClient.dele(from);
				}
			} finally {
				if (ftpClient != null && workingDir != null) {
					ftpClient.changeWorkingDirectory(workingDir);
					pool.returnObject(ftpClient);
				}
			}
		}
	}

	/**
	 * copy文件到另外一个目录
	 * 
	 * @param local
	 * @param remote
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	private boolean uploadFile(File local, String remote) throws Exception {
		String sep = File.separator;
		String localPath = local.getAbsolutePath();
		localPath = localPath.replaceAll("//", sep);
		remote = remote.replaceAll("//", sep);
		// System.out.println("上传："+localPath+"\n"+remote);
		String[] cmd = null;
		if (systemName.indexOf("WINDOWS") != -1) {
			cmd = new String[] { "cmd", "/c", "copy", localPath, remote };
		} else if (systemName.indexOf("LINUX") != -1) {
			cmd = new String[] { "cp", localPath, remote };
		} else {
			throw new Exception("不支持本操作系统 os: " + systemName);
		}

		Process pos = Runtime.getRuntime().exec(cmd);
		if (pos.waitFor() != 0) {
			if (pos.exitValue() == 1) // p.exitValue()==0表示正常结束，1：非正常结束
				throw new Exception("命令执行失败!");
		}
		return true;
	}

}