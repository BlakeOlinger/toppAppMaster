package com.practice;

import java.io.IOException;

class App {
    static void startAllServices() {
        String[] services = {"toppApp.jar", "toppAppDBdaemon.jar", "toppAppUpdater.jar"};

        System.out.println(" Starting Microservices...");
        try {
            System.out.println(" Starting GUI...");
            Runtime.getRuntime().exec("cmd.exe /c " + services[0]);
            System.out.println(" Starting Database Daemon...");
            Runtime.getRuntime().exec("cmd.exe /c " + services[1]);
            System.out.println(" Starting Live Update Daemon...");
            Runtime.getRuntime().exec("cmd.exe /c " + services[2]);

            System.out.println(" Starting SW Daemon...");
            Runtime.getRuntime().exec(" cmd.exe /c sw-part-auto-test.exe");


        } catch (IOException ignore) {
        }


    }
}
