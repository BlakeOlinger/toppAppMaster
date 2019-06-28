package com.practice;

import java.io.IOException;

class App {
    static void startAllServices() {
        String[] services = {"toppApp.jar", "toppAppDBdaemon.jar"};

        try {
            Runtime.getRuntime().exec("cmd.exe /c " + services[0]);

            Runtime.getRuntime().exec("cmd.exe /c " + services[1]);
        } catch (IOException ignore) {
        }
    }
}
