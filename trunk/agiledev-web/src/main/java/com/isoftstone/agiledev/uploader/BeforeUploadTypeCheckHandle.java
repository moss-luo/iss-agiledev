package com.isoftstone.agiledev.uploader;

import java.io.File;

import javax.servlet.ServletRequest;

import com.isoftstone.agiledev.OperationResult;

public class BeforeUploadTypeCheckHandle implements UploadBeforeHandle {

	@Override
	public OperationResult handle(File file, UploadConfig config,
			ServletRequest request) {
		return null;
	}

}
