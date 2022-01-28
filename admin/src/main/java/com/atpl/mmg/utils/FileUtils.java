package com.atpl.mmg.utils;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atpl.mmg.constant.Constants;

public class FileUtils implements Constants {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static void createDir(String path) {
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		file.delete();
	}
}
