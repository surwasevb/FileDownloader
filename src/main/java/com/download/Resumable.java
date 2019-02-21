package com.download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public interface Resumable {

    void pauseDownload(InputStream inputStream) throws IOException;

    void resumeDownload(URL URL, String location) throws IOException;

}
