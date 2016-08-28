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

    JVMShutdownHook jvmShutdownHook = new JVMShutdownHook();
    Runtime.getRuntime().addShutdownHook(jvmShutdownHook);
    jvmShutdownHook.setFileURL(fileURL);
    jvmShutdownHook.setLocation(directoryLocation);

    FileDownloader fileDownloader = new FileDownloader();

    try {

      InputStream inputStream = fileDownloader.startDownload(fileURL, directoryLocation);

      fileDownloader.processStream(inputStream, new File(directoryLocation + File.separator
          + fileDownloader.getFileName()));

    } catch (UnknownHostException e) {
      FileDownloader.status = FileUtility.INPUT_ERROR;
      System.out.println("unable to establish connection with URL of given name");
    } catch (Exception e) {
      FileDownloader.status = FileUtility.INPUT_ERROR;
      System.out.println(e.getMessage());
    }

  }

  private static class JVMShutdownHook extends Thread {

    private String FileURL;
    private String location;

    public String getFileURL() {
      return FileURL;
    }

    public void setFileURL(String fileURL) {
      FileURL = fileURL;
    }

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    }

    public void run() {

      if (FileDownloader.status != FileUtility.DOWNLOAD_COMPLETED
          && FileDownloader.status != FileUtility.INPUT_ERROR) {
        FileDownloader.status = FileUtility.DOWNLOAD_INTERRUPTED;

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
    System.out
        .print(" Enter two parameters one for url and second for download location on machine \n");
    System.exit(0);
  }

}
