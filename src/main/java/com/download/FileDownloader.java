package com.download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author vijay.
 */
public class FileDownloader implements Downloadable, Resumable {

  // size of download buffer.
  private static final int BUFFER_SIZE = 4096;

  // size of download in bytes
  private long size;

  // number of bytes downloaded
  private long downloaded;

  // name of file to be downloaded
  private String fileName;

  // status of downloading
  public static DownloadStatus status;

  public FileDownloader() {
    size = -1;
    downloaded = 0;
  }

  public long getSize() {
    return size;
  }

  public long getDownloaded() {
    return downloaded;
  }

  public String getFileName() {
    return fileName;
  }
  
  public void setSize(long size) {
    this.size = size;
  }

  // establish connection using URL and get InputStream from that connection
  public InputStream startDownload(String fileURL, String location) throws IOException {

    // check directory of given location exists
    if (!FileHelper.checkDirectoryExists(location)) {
      System.out.println(" Directory of given name does not exist ");
      status = DownloadStatus.ERROR;
      throw new IOException();
    }

    URL URLObject = new URL(fileURL);
    HttpURLConnection httpConn = (HttpURLConnection) URLObject.openConnection();

    // get the name of file
    fileName = FileHelper.getFileNameFromURL(URLObject);

    String localFileName = location + File.separator + fileName;
    // check if file exists
    if (FileHelper.checkFileExists(localFileName))
      downloaded = new File(localFileName).length();

    httpConn.setRequestProperty("Range", "bytes=" + downloaded + "-");

    httpConn.connect();
    int responseCode = httpConn.getResponseCode();
    if (responseCode != 200 && responseCode != 206) {
      System.out.println(" Unable to estabish connection with given URL ");
      status = DownloadStatus.ERROR;
      throw new IOException();
    }
    size = downloaded + httpConn.getContentLength();
    fileName = FileHelper.getFileNameFromHeader(httpConn.getHeaderField("Content-Disposition"));
    fileName = fileName.equals("") ? FileHelper.getFileNameFromURL(URLObject) : fileName;
    // opens input stream from the HTTP connection
    InputStream inputStream = httpConn.getInputStream();
    status = DownloadStatus.STARTED;
    return inputStream;
  }

  // read bytes array continuously from input stream and write it into file
  public void processStream(InputStream inputStream, File file) throws IOException {
    if (inputStream == null || file == null) {
      status = DownloadStatus.ERROR;
      return;
    }
    byte[] buffer = new byte[BUFFER_SIZE];
    RandomAccessFile downloadFile = new RandomAccessFile(file, "rw");
    downloadFile.seek(downloaded);

    while (downloaded < size && status == DownloadStatus.STARTED) {
      int readCount = inputStream.read(buffer);
      if (readCount == -1)
        break;

      downloadFile.write(buffer, 0, readCount);
      downloaded += readCount;
      FileHelper.showDownloadProgress(downloaded, size);
    }
    inputStream.close();
    downloadFile.close();
    status = DownloadStatus.COMPLETED;

    return;
  }

 

  // pauses download by closing input stream
  public void pauseDownload(InputStream inputStream) throws IOException {
    inputStream.close();
    status = DownloadStatus.PAUSED;
  }

  // resumes download from URL and at given location
  public void resumeDownload(String URL, String location) throws IOException {
    status = DownloadStatus.RESUMED;
    InputStream inputStream = startDownload(URL, location);
    processStream(inputStream, new File(location + File.separator +fileName));
  }

}
