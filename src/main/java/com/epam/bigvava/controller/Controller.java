package com.epam.bigvava.controller;

import com.epam.bigvava.command.DownloadFile;
import com.epam.bigvava.command.FtpConnection;
import com.epam.bigvava.command.ListOfFiles;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;

/**
 *
 */
public class Controller {
    private static FTPClient ftpClient;

    public boolean connect(String server, String user, String pass){
        FtpConnection ftpConnection = FtpConnection.getInstance();
        if (ftpConnection.connect(server, user, pass) != null){
            ftpClient = ftpConnection.connect(server,user,pass);
            return true;
        }
        return false;
    }

    public FTPFile[] list(String directory){
        ListOfFiles listOfFiles = ListOfFiles.getInstance();
        return listOfFiles.getListOfFiles(ftpClient, directory);
    }

    public boolean download(String filename, String destination){
        DownloadFile downloadFile = DownloadFile.getInstance();
        return downloadFile.download(ftpClient, filename, destination);
    }

    public void disconnect(){
        try {
            ftpClient.logout();
            ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}