/**
 * 
 */
package com.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author vijay
 * 
 *         This is generic interface for downloading the things.
 * 
 */
public interface Downloadable {

	InputStream startDownload(String URL, String location) throws IOException;

	long processStream(InputStream inputStream, File writer) throws IOException;

}
