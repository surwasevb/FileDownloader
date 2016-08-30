package com.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author vijay
 * 
 *         This interface is used for resumable downloads only.
 * 
 */
public interface Resumable {

  void pauseDownload(InputStream inputStream) throws IOException;

  void resumeDownload(URL URL, String location) throws IOException;

}
