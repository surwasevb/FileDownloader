package com.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author vijay
 * 
 *         This is Test class. Performs unit testing for FileDownloader Class
 * 
 */
public class FileDownloaderTest {

  /**
   * @throws IOException
   * 
   *         This test is for processStream function of FileDownloader class.
   * 
   */
  @Test
  public void shouldReadInputStreamAndWriteTOFile() throws IOException {

    FileDownloader fileDownloader = new FileDownloader();

    File inputFile = new File(this.getClass().getResource("/test-input-file.xml").getFile());
    File outputFile = new File("test-output-file.xml");

    fileDownloader.setSize(inputFile.length());
    InputStream inputStream = new FileInputStream(inputFile);
    FileDownloader.status = DownloadStatus.STARTED;
    fileDownloader.processStream(inputStream, outputFile);

    // Assert on whether both file have same length
    assertEquals(inputFile.length(), outputFile.length());

    // closing input stream and deleting outputfile
    inputStream.close();
    Files.delete(Paths.get(outputFile.toURI()));
  }

  /**
   * @throws IOException
   * 
   *         This Test is for pausedownload function of FileDownloader class.
   */
  @Test(expected = IOException.class)
  public void shouldPauseDownload() throws IOException {
    FileDownloader fileDownloader = new FileDownloader();

    File inputFile = new File(this.getClass().getResource("/test-input-file.xml").getFile());
    InputStream inputStream = new FileInputStream(inputFile);
    fileDownloader.pauseDownload(inputStream);

    // asserts whether Status of downloading is PAUSED
    assertEquals(FileDownloader.status, DownloadStatus.PAUSED);

    // expects inputStream to throw IO exception
    inputStream.read();
  }


  /**
   * @throws IOException
   * 
   *         This test is for resumeDownload function of FileDownloader class.
   */
  @Test(expected = IOException.class)
  public void shouldResumeDownload() throws IOException {

    FileDownloader fileDownloader = new FileDownloader();
    String fileURL = this.getClass().getResource("/test-input-file.xml").getFile();    
    try {
      fileDownloader.resumeDownload(fileURL, ".");
    } catch (IOException e) {
      // assert whether status of downloading is STARTED
      assertEquals(FileDownloader.status, DownloadStatus.RESUMED);      
      throw e;
    }
  }
}
