package com.practice;


/*
this microservice daemon is the master controller for the other microservices:
the GUI, DB, Updater and C# SW

This is called from the shortcut and  starts the other services.

this will receive commands via the master.config file and issue out as needed to
other services
 */
public class Main {

    public static void main(String[] args) {

        InitializeDB.checkAndInitializeDB();

      //   Installer.checkAndInstall();

       // new InitializeApp().initializeConfigFiles();

        // App.startAllServices();

       // new MasterDaemon().startMasterDaemon();
    }
}