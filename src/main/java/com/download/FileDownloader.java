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
	public static String status;

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

	// establish connection using URL and get InputStream from that connection
	public InputStream startDownload(String fileURL, String location)
			throws IOException {

		URL URLObject = new URL(fileURL);
		HttpURLConnection httpConn = (HttpURLConnection) URLObject
				.openConnection();

		// get the name of file
		fileName = FileUtility.getFileNameFromURL(URLObject);

		String localFileName = location + File.separator + fileName;
		// check if file exists
		if (FileUtility.checkFileExists(localFileName))
			downloaded = new File(localFileName).length();

		httpConn.setRequestProperty("Range", "bytes=" + downloaded + "-");

		httpConn.connect();
		int responseCode = httpConn.getResponseCode();
		if (responseCode / 100 != 2) {
			return null;
		}
		size = downloaded + httpConn.getContentLength();

		fileName = FileUtility.getFileNameFromHeader(httpConn
				.getHeaderField("Content-Disposition"));
		fileName = fileName.equals("") ? FileUtility
				.getFileNameFromURL(URLObject) : fileName;

		// opens input stream from the HTTP connection
		InputStream inputStream = httpConn.getInputStream();

		status = FileUtility.DOWNLOAD_STARTED;

		return inputStream;
	}

	// read bytes array continuously from input stream and write it into file
	public long processStream(InputStream inputStream, File file)
			throws IOException {

		byte[] buffer = new byte[BUFFER_SIZE];
		RandomAccessFile downloadFile = new RandomAccessFile(file, "rw");
		downloadFile.seek(downloaded);

		while (downloaded < size && status == FileUtility.DOWNLOAD_STARTED) {

			int readCount = inputStream.read(buffer);
			if (readCount == -1)
				break;

			downloadFile.write(buffer, 0, readCount);
			downloaded += readCount;
			FileUtility.showDownloadProgress(downloaded, size);
		}

		inputStream.close();
		downloadFile.close();
		status = FileUtility.DOWNLOAD_COMPLETED;

		return downloaded;
	}

	// pauses download by closing input stream
	public void pauseDownload(InputStream inputStream) throws IOException {
		inputStream.close();
		status = FileUtility.DOWNLOAD_PAUSED;
	}

	// resumes download from URL and at given location
	public void resumeDownload(String URL, String location) throws IOException {

		status = FileUtility.DownlOAD_RESUMED;
		InputStream inputStream = startDownload(URL, location);

		processStream(inputStream, new File(location + fileName));

	}

}