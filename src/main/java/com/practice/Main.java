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

public class Main {

    public static void main(String[] args) {
        Config.isDatabaseInstalled = InitializeDB.checkAndInitializeDB();

        new AppFileStructure().checkAndInstallFileDirectories();

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

        Config.servicesInstalled =
                MicroServicesInstaller.checkAndInstallServices(sources, targets);

      //   Installer.checkAndInstall();

       // new InitializeApp().initializeConfigFiles();

        // App.startAllServices();

       // new MasterDaemon().startMasterDaemon();
    }
}
