package com.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class MicroServicesInstaller {

    private static void run() {
        var toppAppDBPath = "toppAppDBdaemon/programFiles/bin/toppApp.jar";
        var GUIinstallFile = "toppApp.jar";

        var DBdaemonDBpath = "toppAppDBdaemon/programFiles/bin/toppAppDBdaemon.jar";
        var DBinstallFile = "toppAppDBdaemon.jar";

        var updaterDBPath = "toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar";
        var updaterInstallFile = "toppAppUpdater.jar";

        System.out.println(" Checking Installed Microservices...");

        if(new File(GUIinstallFile).exists()) {
            System.out.println(" Microservices Installed");
        } else {
            installMicroservice(toppAppDBPath, GUIinstallFile, "GUI");

            installMicroservice(DBdaemonDBpath, DBinstallFile, "Database");

            installMicroservice(updaterDBPath, updaterInstallFile, "Live Update");
        }
    }

    static void checkAndInstallServices() {
        run();
    }

    private static void copyFiles(String original, String copy) {

        try (var originalFile = new FileInputStream(original);
             var copyFile = new FileOutputStream(copy)){
            int readByte;
            do {
                readByte = originalFile.read();
                if(readByte != -1) {
                    copyFile.write(readByte);
                }
            } while (readByte != -1);

        } catch (IOException ignore) {
        }

    }

    private static void installMicroservice(String original, String copy, String microservice) {
        if(!new File(copy).exists()) {
            System.out.println(" TOPP App: " + microservice + " Microservice Not Installed - Installing...");
            copyFiles(original, copy);
            if (new File(copy).exists()) {
                System.out.println(" TOPP App: " + microservice + " Microservice Installed ");
            } else {
                System.out.println(" ERROR: TOPP App: " + microservice +  " Microservice Was Not Installed");
            }
        }
    }
}
