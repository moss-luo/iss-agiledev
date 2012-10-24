package com.isoftstone.agiledev.config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author hilbert.xu.wang@gmail.com
 *
 */
public class SysUtils {
	
	public static final String USER_HOME = System.getProperty("user.home");
	
	public static File getExternalConfigDir()
	{
		File f = new File(USER_HOME + "/iss-agiledev");
		if(!f.exists())
			f.mkdir();
		return f;
	}
	
	public static File getConfigContext()
	{
		String fileStr = USER_HOME + "/iss-agiledev/configContext.properties";
		File file = new File(fileStr);
		if(!file.exists())
		{
			getExternalConfigDir();
			try {
				file.createNewFile();
				if(!file.canWrite())
					file.setWritable(true, true);
				FileWriter writer = new FileWriter(file);
				writer.write("\nAdd the config items as following\n#callbacks.path=virgo home");
				writer.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return file;
	}

}
