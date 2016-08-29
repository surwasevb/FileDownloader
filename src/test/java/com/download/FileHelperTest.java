package com.download;

import java.io.IOException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @author vijay
 * 
 * This is Test class. Performs unit testing for FileHelper Class
 *
 */
public class FileHelperTest {
  
  @Test
  public void shouldGetFileNameFromURL() throws IOException {
    
    String fileName = FileHelper.getFileNameFromURL(new URL("http://www.xyz.com/abc.txt"));
    
    assertEquals(fileName,"abc.txt");    
  }
  
  @Test
  public void shouldGetFileNameFromHeader() {
    String dispostionHeaderExample = "Content-Disposition: attachment/; filename=" + "\"fname.txt\"";
    
    assertEquals(FileHelper.getFileNameFromHeader(dispostionHeaderExample),"fname.txt");
    
  }
  
  @Test
  public void shouldCheckIfFileExists() {
   
    String checkFile = this.getClass().getResource("/test-input-file.xml").getFile();
    assertEquals(FileHelper.checkFileExists(checkFile),true);
   
    checkFile = "/test-output.xml";
    assertEquals(FileHelper.checkFileExists(checkFile),false);
  }  
}
