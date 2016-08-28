package com.download;

import java.io.File;
import java.net.URL;

/**
 * @author vijay
 * 
 *         This class provides utility functions on file.
 * 
 */
public class FileHelper {

  private static int fileNameHeaderLength = 10;

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
        fileName = disposition.substring(index + fileNameHeaderLength, disposition.length() - 1);
      }
    }
    return fileName;
  }

  // check whether file of given name exists in the machine
  public static boolean checkFileExists(String fileName) {
    File file = new File(fileName);
    return file.exists() && !file.isDirectory();
  }

  // check whether directory of given name exists in the machine
  public static boolean checkDirectoryExists(String directoryName) {
    File file = new File(directoryName);
    return file.exists() && file.isDirectory();
  }

  // show progress of download
  public static void showDownloadProgress(long downloaded, long size) {
    int percentageOfDownload = (int) (downloaded * 100 / size);
    System.out.print("\r");
    System.out.print(" Downloading " + percentageOfDownload + "%");
    if (downloaded == size) {
      System.out.print("\r");
      System.out.print(" Download Complete \n");
    }
    return;
  }

}
