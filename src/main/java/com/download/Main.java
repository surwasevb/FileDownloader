package com.download;

import java.io.File;
import java.io.InputStream;

/**
 * @author vijay
 * 
 *         This is main class that trigger download. It requires two arguments.
 *         one is for URL and second is for directory location.
 * 
 */
public class Main {

	public static void main(String[] args) {

		if (args.length != 2) {
			error();
		}

		String fileURL = args[0];
		String directoryLocation = args[1];
		System.out.println("############## Downloading file from URL ##############");
		FileDownloader fileDownloader = new FileDownloader();

		try {

			InputStream inputStream = fileDownloader.startDownload(fileURL);

			fileDownloader.processStream(inputStream,
					new File(directoryLocation + File.separator
							+ fileDownloader.getFileName()));

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	private static void error() {
		System.out.println("Error");
		System.out
				.println("Enter two parameters one for url and second for download location on machine");
		System.exit(0);
	}

}
