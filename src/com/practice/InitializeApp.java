package com.practice;

import java.io.*;

public class InitializeApp implements Runnable{
    private final Thread thread;

    InitializeApp() {
        thread = new Thread(this, "App Config Init");
    }

    void initializeConfigFiles() {
        thread.start();

    }
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Config.errorCount = 0;

        System.out.println(" Initializing App - Initializing Config Files");
        var DBname = "DBdaemon.config";
        var masterName = "master.config";
        var GUIname = "GUI.config";
        var updaterName = "updater.config";

        try (var DBconfig = new FileOutputStream(Config.configFilePath + "DBdaemon.config");
             var masterConfig = new FileOutputStream(Config.configFilePath + "master.config");
             var guiConfig = new FileOutputStream(Config.configFilePath + "GUI.config");
             var updaterConfig = new FileOutputStream(Config.configFilePath + "updater.config")) {

            char command = '0';
            int databasePushState = (int) '1';

            DBconfig.write((int)command);
            DBconfig.write(databasePushState);

            masterConfig.write((int)command);
            masterConfig.write(databasePushState);

            guiConfig.write((int)command);

            updaterConfig.write((int) command);
            updaterConfig.write(databasePushState);

            confirmConfigFileInitialization(Config.configFilePath + DBname, DBname);
            confirmConfigFileInitialization(Config.configFilePath + masterName, masterName);
            confirmConfigFileInitialization(Config.configFilePath + GUIname, GUIname);
            confirmConfigFileInitialization(Config.configFilePath + updaterName, updaterName);


             } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Initialize Config Files");
            ++Config.errorCount;
        }

        System.out.println("Master Daemon - App Initializer Completed with - " + Config.errorCount + " Errors");
    }

    private static void confirmConfigFileInitialization(String path, String name) {
        System.out.println(" Confirming Initialization For - " + name);
        if(new File(path).exists()) {

            System.out.println(" " + name + " Config File Found - Initializing...");

            try (var configFile = new FileInputStream(path)) {

                if(String.valueOf((char) configFile.read()).compareTo("0") == 0) {
                    System.out.println(" " + name + " Successfully Initialized");
                } else {
                    System.out.println("ERROR: " + name + " Could Not Be Initialized");
                    ++Config.errorCount;
                }

            } catch (IOException ignore) {
                System.out.println(" ERROR: Could Not Open " + name);
                ++Config.errorCount;
            }

        } else {
            System.out.println(" ERROR: File " + name + " Not Found");
            ++Config.errorCount;
        }
    }
}
