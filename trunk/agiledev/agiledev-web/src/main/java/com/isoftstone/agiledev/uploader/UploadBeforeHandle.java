package com.isoftstone.agiledev.uploader;

import java.io.File;

import javax.servlet.ServletRequest;

import com.isoftstone.agiledev.OperationResult;

/**
 * 上传组件兼容业务的封装，如果业务需要，在相关配置中配置实现类
 * @author sinner
 *
 */
public interface UploadBeforeHandle {
	/**
	 * 上传之前调用，可以对文件进行校验等操作
	 * @param file 服务器端临时文件，上传完成后，该文件会被删除
	 * @param config web.xml中filter的init-param配置或者struts.xml中param的所有配置
	 * @param request 如果是UploadFilter上传，则该参数为UploadServletRequest实例
	 * @return OperationResult 如果不允许上传，请指定setSuccess(false)
	 * @see com.isoftstone.agiledev.uploader.UploadServletRequest
	 * @see com.isoftstone.agiledev.uploader.UploadConfig
	 * @see com.isoftstone.agiledev.OperationResult
	 */
	OperationResult handle(File file,UploadConfig config,ServletRequest request);
}
