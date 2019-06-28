package com.practice;

class Installer {
    static void checkAndInstall() {
        // new InitializeDB().checkAndInitializeDB();
        var fileStructure = new AppFileStructure();
        fileStructure.checkAndInstallFileDirectories();
    }
}
