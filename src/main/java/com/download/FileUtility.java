/**
 * 
 */
package com.download;

import java.io.File;
import java.net.URL;

/**
 * @author vijay
 * 
 *         This class provides utility functions on file.
 * 
 */
public class FileUtility {

	// Get file name from portion of URL.
	public static String getFileNameFromURL(URL url) {

		String fileName = url.getFile();
		return fileName.substring(fileName.lastIndexOf('/') + 1);
	}

	// Get file name from header of the http response
	public static String getFileNameFromHeader(String disposition) {

		String fileName = "";
		if (disposition != null) {

			int index = disposition.indexOf("filename=");

			if (index > 0) {
				fileName = disposition.substring(index + 10,
						disposition.length() - 1);
			}

		}
		return fileName;
	}

	// check whether file of given name exists in the machine
	public static boolean checkFileExists(String fileName) {

		File file = new File(fileName);
		if (file.exists() && !file.isDirectory()) {
			return true;
		}
		return false;
	}

	// show progress of download
	public static void showDownloadProgress(long downloaded, long size) {
		System.out.print(".");
		return;
	}

}
