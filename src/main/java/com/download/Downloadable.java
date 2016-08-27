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
	
	long processStream(InputStream inputStream, Writer writer);

}
