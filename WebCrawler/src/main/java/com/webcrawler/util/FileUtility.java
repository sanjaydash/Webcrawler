package com.webcrawler.util;

import java.io.File;
import java.net.URL;

import com.webcrawler.task.WebCrawler;

public class FileUtility {

	/* Utility method to return the File Object from file name */
	public static File getFileFromResources(String fileName) {

		ClassLoader classLoader = WebCrawler.class.getClassLoader();

		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file is not found!");
		} else {
			return new File(resource.getFile());
		}

	}

}
