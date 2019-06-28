package com.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class MicroServicesInstaller {

    private void run() {
        var toppAppDBPath = "toppAppDBdaemon/programFiles/bin/toppApp.jar";
        var GUIinstallFile = "toppApp.jar";

        var DBdaemonDBpath = "toppAppDBdaemon/programFiles/bin/toppAppDBdaemon.jar";
        var DBinstallFile = "toppAppDBdaemon.jar";

        var updaterDBPath = "toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar";
        var updaterInstallFile = "toppAppUpdater.jar";

        if(!new File(GUIinstallFile).exists())
            copyFiles(toppAppDBPath, GUIinstallFile);

        if(!new File(DBinstallFile).exists())
            copyFiles(DBdaemonDBpath, DBinstallFile);

        if(!new File(updaterInstallFile).exists())
            copyFiles(updaterDBPath, updaterInstallFile);
    }

    void checkAndInstallServices() {
        run();
    }

    private void copyFiles(String original, String copy) {

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
}
