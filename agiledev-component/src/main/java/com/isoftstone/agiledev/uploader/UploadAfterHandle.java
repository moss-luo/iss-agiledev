package com.isoftstone.agiledev.uploader;

import javax.servlet.ServletRequest;

/**
 * 上传组件最后一个环节
 * @author sinner
 *
 */

public interface UploadAfterHandle {
	/**
	 * 上传之后，review之前调用。
	 * @param fileName 考虑到远程上传，所以只回传文件名，如果需要整个文件路径，可以如下操作：
	 *                 <b>config.parseRemotePath(request)+fileName<b>可以拿到整个文件路径;
	 * @param config web.xml中filter的init-param配置或者struts.xml中param的所有配置
	 * @param request 如果是UploadFilter上传，则该参数为UploadServletRequest实例
	 * @see com.isoftstone.agiledev.uploader.UploadServletRequest
	 * @see com.isoftstone.agiledev.uploader.UploadConfig
	 */
	
	void handle(String fileName,UploadConfig config,ServletRequest request);
}
