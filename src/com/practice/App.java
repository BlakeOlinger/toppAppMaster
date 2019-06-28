package com.practice;

import java.io.FileOutputStream;
import java.io.IOException;

class App {
    static void startAllServices() {
        String[] services = {"toppApp.jar", "toppAppDBdaemon.jar"};

        try (var DBconfig = new FileOutputStream(Config.installDirectory + "DBdaemon.config");
        var masterConfig = new FileOutputStream(Config.installDirectory + "master.config");
        var guiConfig = new FileOutputStream(Config.installDirectory + "GUI.config")){
            Runtime.getRuntime().exec("cmd.exe /c " + services[0]);

            Runtime.getRuntime().exec("cmd.exe /c " + services[1]);

            char command = '0';
            DBconfig.write((int) command);
            masterConfig.write((int) command);
            guiConfig.write((int) command);

        } catch (IOException ignore) {
        }
    }
}
