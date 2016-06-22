package com.epam.bigvava.command;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import java.io.IOException;

/**
 *
 */
public class FtpConnection {
    private static final int PORT = 21;
    private static FtpConnection instance = new FtpConnection();

    private FtpConnection() {

    }

    public static FtpConnection getInstance() {
        return instance;
    }

    public FTPClient connect(String server, String user, String pass){
        int reply;
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(server, PORT);
            ftpClient.enterLocalPassiveMode();
            reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                return null;
            } else {
                if (ftpClient.login(user, pass)) {
                    return ftpClient;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
