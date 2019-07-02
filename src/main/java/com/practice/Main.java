package com.practice;

/*
this microservice daemon is the master controller for the other microservices:
the GUI, DB, Updater and C# SW

This is called from the shortcut and  starts the other services.

this will receive commands via the master.config file and issue out as needed to
other services
 */

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        checkForAndInstallLocalDatabase();

        checkForAndInstallApplicationFileDirectories();

        checkForAndInstallApplicationMicroservices();

        if(Config.isDatabaseInstalled &&
        !Config.areServicesInstalled.contains(Boolean.FALSE)) {

            initializeConfigFiles();

            startApplicationMicroservices();

            startMasterDaemon();
        }
    }

    private static void startMasterDaemon() {
        new MasterDaemon().startMasterDaemon();
    }

    private static void startApplicationMicroservices() {
        var microservices = new String[] {
                "toppApp.jar",
                "toppAppUpdater.jar",
                "toppAppDBdaemon.jar"
        };

        for(String name: microservices)
            new App(name).startMicroservice();
    }

    private static void checkForAndInstallApplicationFileDirectories() {
        new AppFileStructure().checkAndInstallFileDirectories();
    }

    private static void checkForAndInstallLocalDatabase() {
        Config.isDatabaseInstalled = InitializeDB.checkAndInitializeDB();
    }

    private static void checkForAndInstallApplicationMicroservices() {
        var sources = new Path[] {
                Paths.get("toppAppDBdaemon/programFiles/bin/toppApp.jar"),
                Paths.get("toppAppDBdaemon/programFiles/bin/toppAppDBdaemon.jar"),
                Paths.get("toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar")
        };

        var targets = new Path[] {
                Paths.get("toppApp.jar"),
                Paths.get("toppAppDBdaemon.jar"),
                Paths.get("toppAppUpdater.jar")
        };

        for(var i = 0; i < sources.length; ++i)
            new MicroServicesInstaller(sources[i], targets[i]).install();
    }

     private static void initializeConfigFiles() {
        var pathBase = "programFiles/config/";
        var configFilePaths = new ArrayList<Path>();
        configFilePaths.add(Paths.get(pathBase + "DBdaemon.config"));
        configFilePaths.add(Paths.get(pathBase + "master.config"));
        configFilePaths.add(Paths.get(pathBase + "GUI.config"));
        configFilePaths.add(Paths.get(pathBase + "updater.config"));

        var commands = new ArrayList<StringBuilder>();
        commands.add(new StringBuilder("01"));
        commands.add(new StringBuilder("01"));
        commands.add(new StringBuilder("0"));
        commands.add(new StringBuilder("0"));

        for(var i = 0; i < configFilePaths.size(); ++i) {
            new InitializeApp(configFilePaths.get(i), commands.get(i))
                    .initializeConfigFile();
        }
    }
}
