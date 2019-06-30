package com.practice;

class Installer {
    static void checkAndInstall() {
       InitializeDB.checkAndInitializeDB();

        new AppFileStructure().checkAndInstallFileDirectories();

       MicroServicesInstaller.checkAndInstallServices();

        System.out.println("Master Daemon - Installer Completed with - " + Config.errorCount + " Errors");
    }
}
