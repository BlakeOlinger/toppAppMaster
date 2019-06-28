package com.practice;

import java.io.IOException;

class App {
    static void startAllServices() {
        String[] services = {"toppApp.jar", "toppAppDBdaemon.jar", "toppAppUpdater.jar"};

        try {
            Runtime.getRuntime().exec("cmd.exe /c " + services[0]);
            Runtime.getRuntime().exec("cmd.exe /c " + services[1]);
            Runtime.getRuntime().exec("cnd.exe /c " + services[2]);

        } catch (IOException ignore) {
        }


    }
}
