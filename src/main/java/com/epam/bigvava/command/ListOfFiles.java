package com.epam.bigvava.command;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

/**
 *
 */
public class ListOfFiles {
    private static ListOfFiles instance = new ListOfFiles();

    private ListOfFiles(){

    }

    public static ListOfFiles getInstance(){
        return instance;
    }

    public FTPFile[] getListOfFiles(FTPClient ftpClient, String directory){
        FTPFile[] files = null;
        try {
            files = ftpClient.listFiles(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
