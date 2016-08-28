package com.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author vijay
 * 
 *         This is main class that trigger download. It requires two arguments. one is for URL and
 *         second is for directory location.
 * 
 */
public class Main {

  public static void main(String[] args) {

    if (args.length != 2) {
      error();
    }

    String fileURL = args[0];
    String directoryLocation = args[1];
    System.out.println("############## Downloading file from URL ##############");

    addShutDownHook(fileURL, directoryLocation);

    FileDownloader fileDownloader = new FileDownloader();
    try {
      InputStream inputStream = fileDownloader.startDownload(fileURL, directoryLocation);
      fileDownloader.processStream(inputStream, new File(directoryLocation + File.separator
          + fileDownloader.getFileName()));
    } catch (UnknownHostException e) {
      FileDownloader.status = DownloadStatus.ERROR;
      System.out.println("unable to establish connection with URL of given name");
    } catch (Exception e) {
      FileDownloader.status = DownloadStatus.ERROR;
      System.out.println(e.getMessage());
    }

  }

  private static void addShutDownHook(String fileURL, String directoryLocation) {
    JVMShutdownHook jvmShutdownHook = new JVMShutdownHook();
    jvmShutdownHook.setFileURL(fileURL);
    jvmShutdownHook.setLocation(directoryLocation);
    Runtime.getRuntime().addShutdownHook(jvmShutdownHook);
  }

  private static class JVMShutdownHook extends Thread {

    private String fileURL;
    private String location;

    public String getFileURL() {
      return fileURL;
    }

    public void setFileURL(String fileURL) {
      this.fileURL = fileURL;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public void run() {
      if (FileDownloader.status != DownloadStatus.COMPLETED
          && FileDownloader.status != DownloadStatus.ERROR) {
        FileDownloader.status = DownloadStatus.INTERRUPTED;
        System.out.println(" Download is not completed. Do you want to abort? (yes/no): ");
        if (new Scanner(System.in).next().equalsIgnoreCase("no")) {
          FileDownloader fileDownloader = new FileDownloader();
          try {
            fileDownloader.resumeDownload(this.getFileURL(), this.getLocation());
          } catch (IOException e) {
            System.out.println(e.getMessage());
          }
        }
      }
    }
  }

  private static void error() {
    System.out.print(" Invalid input parameters : ");
    System.out.print(" <Command> <URL> <location>\n");
    System.exit(0);
  }

}
