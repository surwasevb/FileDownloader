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
public interface Downloadable {
	
	InputStream start(String URL);	

	void pause(InputStream inputStream, Writer writer);
	
	void resume(String URL, String location);
	
	long processStream(InputStream inputStream, Writer writer);

}
