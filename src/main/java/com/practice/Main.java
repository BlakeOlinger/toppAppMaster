package com.practice;

/*
this microservice daemon is the master controller for the other microservices:
the GUI, DB, Updater and C# SW

This is called from the shortcut and  starts the other services.

this will receive commands via the master.config file and issue out as needed to
other services
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    static String userRoot = "";
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        logger.log(Level.INFO, "Main Thread - Start");

//        liveUpdateTest();


        checkForAndInstallLocalDatabase();

        checkForAndInstallApplicationFileDirectories();

        checkForAndInstallApplicationMicroservices();

        checkForAndInstallSWMicroservice();

        checkForAndInstallToppAppBat();

        if(Config.isDatabaseInstalled &&
        !Config.areServicesInstalled.contains(Boolean.FALSE)) {

            initializeConfigFiles();

            startMasterDaemon();
        }

        if (!Config.isMasterUpdate)
            shutdownMicroservices();

        logger.log(Level.INFO, "Main Thread - Exit");
    }

    private static void liveUpdateTest() {
        try {
            var path = Paths.get(userRoot + "test.txt");
            Files.createFile(path);
        } catch (IOException ignore) {
        }
    }

    private static void checkForAndInstallToppAppBat() {
        var target = Paths.get(userRoot + "ToppApp.bat");
        var source = Paths.get(userRoot
                + "toppAppDBdaemon/programFiles/ToppApp.bat");
        if (!Files.exists(target)) {
            try {
                Files.copy(source, target);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error Copying ToppApp.bat to EXE Directory",
                        e);
            }
        }
    }

    private static void checkForAndInstallSWMicroservice() {
        logger.log(Level.INFO, "SW Installer - Start");

        var targetRoot = userRoot + "programFiles/sw/";
        var sourceRoot = userRoot + "toppAppDBdaemon/programFiles/sw/";
        var names = new String[] {
                "hostfxr.dll",
                "hostpolicy.dll",
                "NLog.config",
                "SolidWorks.Interop.sldworks.dll",
                "SolidWorks.Interop.swconst.dll",
                "sw-part-auto-test.deps.json",
                "sw-part-auto-test.dll",
                "sw-part-auto-test.exe",
                "sw-part-auto-test.pdb",
                "sw-part-auto-test.runtimeconfig.dev.json",
                "sw-part-auto-test.runtimeconfig.json"
        };
        var targetPaths = new ArrayList<Path>();
        var sourcePaths = new ArrayList<Path>();

        for(String name : names) {
            targetPaths.add(
                    Paths.get(targetRoot + name)
            );
            sourcePaths.add(
                    Paths.get(sourceRoot + name)
            );
        }

        var swInstaller = new ArrayList<MicroServicesInstaller>();

        for(var i = 0; i < names.length; ++i)
            swInstaller.add(new MicroServicesInstaller(
                    sourcePaths.get(i),
                    targetPaths.get(i)
            ));

        for(MicroServicesInstaller servicesInstaller : swInstaller)
            servicesInstaller.install();

        for(MicroServicesInstaller servicesInstaller : swInstaller)
            servicesInstaller.join();

        logger.log(Level.INFO, "SW Installer - Exit");
    }

    private static void shutdownMicroservices() {
        var configRoot = userRoot + "programFiles/config/";
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

    private static void checkForAndInstallApplicationFileDirectories() {
        logger.log(Level.INFO, "Install App Directories - Start");

        var appFiles = new AppFileStructure();
        appFiles.checkAndInstallFileDirectories();

        appFiles.join();

        logger.log(Level.INFO, "Install App Directories - Exit");
    }

    private static void checkForAndInstallLocalDatabase() {
        logger.log(Level.INFO, "Initializing Local Database - Start");

        if (InternetCheck.isConnected())
            Config.isDatabaseInstalled = InitializeDB.checkAndInitializeDB();
        else
            logger.log(Level.SEVERE, "Error Could Not Install Local Database Instance" +
                    " - No Network Connection Detected");

        logger.log(Level.INFO, "Initializing Local Database - Exit");
    }

    private static void checkForAndInstallApplicationMicroservices() {
        logger.log(Level.INFO, "Install App Microservices - Start");

        var sources = new Path[] {
                Paths.get(userRoot + "toppAppDBdaemon/programFiles/bin/toppApp.jar"),
                Paths.get(userRoot + "toppAppDBdaemon/programFiles/bin/toppAppDBdaemon.jar"),
                Paths.get( userRoot + "toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar")
        };

        var targets = new Path[] {
                Paths.get(userRoot + "toppApp.jar"),
                Paths.get(userRoot + "toppAppDBdaemon.jar"),
                Paths.get(userRoot + "toppAppUpdater.jar")
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

        var pathBase = userRoot + "programFiles/config/";
        var configFilePaths = new ArrayList<Path>();
        configFilePaths.add(Paths.get(pathBase + "DBdaemon.config"));
        configFilePaths.add(Paths.get(pathBase + "master.config"));
        configFilePaths.add(Paths.get(pathBase + "GUI.config"));
        configFilePaths.add(Paths.get(pathBase + "updater.config"));
        configFilePaths.add(Paths.get(pathBase + "SWmicroservice.config"));

        var commands = "011";

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
