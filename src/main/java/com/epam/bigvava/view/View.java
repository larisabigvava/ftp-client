package com.epam.bigvava.view;

import com.epam.bigvava.controller.Controller;
import org.apache.commons.net.ftp.FTPFile;
import java.util.Scanner;

/**
 *
 */

public class View {
    private Controller controller = new Controller();
    private Scanner scanner = new Scanner(System.in);

    private String directory ="\\";
    private FTPFile[] files;

    public void start(){
        String user = "anonymous";
        String pass = "anonymous";
        String server;

        System.out.println("Server (example: ftp.4d.com): ");
        server = scanner.nextLine();
        printMenu();
        if (controller.connect(server, user, pass)){
            files = controller.list(directory);
            printDirectoriesAndFiles(files);
            String filename;
            while (scanner.hasNext()){
                filename = scanner.nextLine();
                switch (filename) {
                    case "exit":
                        controller.disconnect();
                        return;
                    case "..":
                        rollback();
                        break;
                    case "/":
                        toRoot();
                        break;
                    default:
                        searchFileOrDirectory(filename);
                }
            }
        } else {
            System.out.println("Could not connect or login to the server");
        }
    }

    private void printMenu(){
        System.out.println("Print:");
        System.out.println("Directory name to enter");
        System.out.println("File name to downloading");
        System.out.println("'..' to return to previous directory");
        System.out.println("'/' to return to root directory");
        System.out.println("'exit' to stop the application\n");
    }

    public void printDirectoriesAndFiles(FTPFile[] files){
        if (files.length != 0) {
            for (FTPFile file : files) {
                String details = file.getName();
                if (file.isDirectory()) {
                    details = "[" + details + "]";
                }
                System.out.println(details);
            }
        } else {
            System.out.println("There is no any file or directory");
        }
    }

    private void rollback(){
        directory = directory.substring(0, directory.lastIndexOf("\\"));
        directory = directory.substring(0, directory.lastIndexOf("\\")) + "\\";
        files = controller.list(directory);
        printDirectoriesAndFiles(files);
    }

    private void searchFileOrDirectory(String filename){
        boolean isValidFileName = false;
        if (files.length != 0) {
            for (FTPFile file : files) {
                if (filename.equals(file.getName())) {
                    if (file.isDirectory()) {
                        isValidFileName = true;
                        directory += file.getName() + "\\";
                        files = controller.list(directory);
                        printDirectoriesAndFiles(files);
                    } else if (file.isFile()) {
                        isValidFileName = true;
                        String destination;
                        System.out.println("Destination to download: ");
                        destination = scanner.nextLine();
                        if (controller.download(directory + filename, destination + "\\" + filename)) {
                            System.out.println("File " + filename + " was downloaded.");
                        } else {
                            System.out.println("Error while downloading.");
                        }
                    }
                }
            }
        }
        if (!isValidFileName){
            System.out.println("It is not valid filename");
        }
    }

    private void toRoot(){
        directory = "\\";
        files = controller.list(directory);
        printDirectoriesAndFiles(files);
    }
}
