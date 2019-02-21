package com.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface Downloadable {

    InputStream startDownload(URL URL, String location) throws IOException;

    void processStream(InputStream inputStream, File writer) throws IOException;

}
