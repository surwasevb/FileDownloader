/**
 * 
 */
package com.download;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author vijay
 * 
 *         This interface is used for resumable downloads only.
 * 
 */
public interface Resumable {

  void pauseDownload(InputStream inputStream) throws IOException;

  void resumeDownload(String URL, String location) throws IOException;

}
