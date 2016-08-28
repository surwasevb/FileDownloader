# FileDownloader

This is command line utility for downloading file from URL. User need to specify the URL and directory location in the input.

#### Features
  - User can pause and resume the downloading.
  - Utility is able to resume the download of partially downloaded file.
  - Utility can show the progress of downloading.
  
#### Usage
Use below command to execute utlity
```
$ java -jar <jar-file-name> <URL> <location>
```
Use Ctrl + c to pause the download. 
#### Build Utility

Use below command to build source code
For linux environment
```
./gradlew clean build
```
For windows environment
```
gradlew.bat clean build
```


**Note: For Resumable download server must allow interrupted download. We can check this by command `curl -I <URL>`. If it returns header 'Content-Range: bytes' then only interrupted download can be possible from that server**
