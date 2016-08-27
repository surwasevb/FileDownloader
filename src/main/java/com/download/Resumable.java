/**
 * 
 */
package com.download;

import java.io.InputStream;
import java.io.Writer;

/**
 * @author vijay
 *
 */
public interface Resumable {
	
	void pause(InputStream inputStream, Writer writer);
	
	void resume(String URL, String location);

}
