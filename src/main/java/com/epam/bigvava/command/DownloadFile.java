package com.epam.bigvava.command;

import org.apache.commons.net.ftp.FTPClient;
import java.io.*;

/**
 *
 */
public class DownloadFile {
    private static DownloadFile instance = new DownloadFile();

    private DownloadFile() {

    }

    public static DownloadFile getInstance() {
        return instance;
    }

    public boolean download(FTPClient ftpClient, String filename, String destination) {
        boolean result = true;
        try (FileOutputStream fos = new FileOutputStream(destination)) {
            ftpClient.retrieveFile(filename, fos);
        } catch (IOException e) {
            result = false;
        }
        return result;
    }
}
