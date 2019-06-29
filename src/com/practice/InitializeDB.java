package com.practice;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

class InitializeDB {

    void checkAndInitializeDB() {
        run();
    }

    private void run() {

        System.out.println(" Checking if toppAppDBdaemon/.git exists...");

        var gitDB = new File("toppAppDBdaemon/.git");

        if(!gitDB.exists()) {
            System.out.println("    Database Not Found");

            System.out.println(" Checking network status...");



            try {
                InetAddress.getByName("www.google.com");

                System.out.println(" Connection Available - Downloading DB");

                var process = Runtime.getRuntime().exec(
                        "cmd.exe /c git clone https://github.com/BlakeOlinger/toppAppDBdaemon.git");
                process.waitFor();

                if(gitDB.exists()) {
                    System.out.println(" Database Successfully Installed");
                } else {
                    System.out.println(" ERROR: Database Install Unsuccessful");
                }

            } catch (IOException ignore) {
                System.out.println(" No Connection Available");
            } catch (InterruptedException ignore) {
                System.out.println(" ERROR: Executing Thread did not wait for Database download to finish");
            }

        } else {
            System.out.println("    Database Found");
        }
    }
}
