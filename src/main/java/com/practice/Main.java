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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        logger.log(Level.INFO, "Main Thread - Start");

        checkForAndInstallLocalDatabase();

        checkForAndInstallApplicationFileDirectories();

        checkForAndInstallApplicationMicroservices();

        if(Config.isDatabaseInstalled &&
        !Config.areServicesInstalled.contains(Boolean.FALSE)) {

            initializeConfigFiles();

            startApplicationMicroservices();

            startMasterDaemon();
        }

        shutdownMicroservices();

        logger.log(Level.INFO, "Main Thread - Exit");
    }

    private static void shutdownMicroservices() {
        var configRoot = "programFiles/config/";
        var names = new String[] {
                "DBdaemon.config",
                "GUI.config",
                "master.config",
                "SWmicroservice.config",
                "updater.config"
        };
        var paths = new ArrayList<Path>();
        var microservices = new ArrayList<ProgramState>();
        var index = 0;

        for(String name : names) {
            paths.add(Paths.get(configRoot + name));
            microservices.add(new ProgramState(paths.get(index),
                    name));
            microservices.get(index++).shutdown();
        }

        for(ProgramState microservice : microservices)
            microservice.join();
    }

    private static void startMasterDaemon() {
        var daemon = new MasterDaemon();

        daemon.startMasterDaemon();

        daemon.join();
    }

    private static void startApplicationMicroservices() {
        logger.log(Level.INFO, "Microservices Threads - Start");

//        var microservices = new String[] {
//                "toppApp.jar",
//                "toppAppUpdater.jar",
//                "toppAppDBdaemon.jar",
//                "sw-part-auto-test.exe"
//        };
//
//        var apps = new ArrayList<App>();
//
//        for(String name: microservices)
//           apps.add(new App(name));
//
//        for(App app : apps)
//            app.startMicroservice();

        logger.log(Level.INFO, "Microservice Threads - Exit");
    }

    private static void checkForAndInstallApplicationFileDirectories() {
        logger.log(Level.INFO, "Install App Directories - Start");

        var appFiles = new AppFileStructure();
        appFiles.checkAndInstallFileDirectories();

        appFiles.join();

        logger.log(Level.INFO, "Install App Directories - Exit");
    }

    private static void checkForAndInstallLocalDatabase() {
        logger.log(Level.INFO, "Initializing Local Database - Start");

        Config.isDatabaseInstalled = InitializeDB.checkAndInitializeDB();

        logger.log(Level.INFO, "Initializing Local Database - Exit");
    }

    private static void checkForAndInstallApplicationMicroservices() {
        logger.log(Level.INFO, "Install App Microservices - Start");

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

        var microservices = new ArrayList<MicroServicesInstaller>();

        for(var i = 0; i < sources.length; ++i)
            microservices.add(new MicroServicesInstaller(sources[i], targets[i]));

        for(MicroServicesInstaller servicesInstaller : microservices)
            servicesInstaller.install();

        for(MicroServicesInstaller servicesInstaller : microservices)
            servicesInstaller.join();

        logger.log(Level.INFO, "Install App Microservices - Exit");
    }

     private static void initializeConfigFiles() {
        logger.log(Level.INFO, "Initialize Config Files - Start");

        var pathBase = "programFiles/config/";
        var configFilePaths = new ArrayList<Path>();
        configFilePaths.add(Paths.get(pathBase + "DBdaemon.config"));
        configFilePaths.add(Paths.get(pathBase + "master.config"));
        configFilePaths.add(Paths.get(pathBase + "GUI.config"));
        configFilePaths.add(Paths.get(pathBase + "updater.config"));
        configFilePaths.add(Paths.get(pathBase + "SWmicroservice.config"));

        var commands = "01";

        var initList = new ArrayList<InitializeApp>();

         for (Path configFilePath : configFilePaths) {
             initList.add(new InitializeApp(configFilePath, commands));
         }

         for (InitializeApp app : initList) {
             app.initializeConfigFile();
         }

         for (InitializeApp app : initList) {
             app.join();
         }

         logger.log(Level.INFO, "Initialize Config Files - Exit");
    }
}
