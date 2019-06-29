package com.practice;

class Installer {
    static void checkAndInstall() {
       InitializeDB.checkAndInitializeDB();

        new AppFileStructure().checkAndInstallFileDirectories();

       MicroServicesInstaller.checkAndInstallServices();
    }
}
