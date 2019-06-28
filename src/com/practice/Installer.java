package com.practice;

class Installer {
    static void checkAndInstall() {
        new InitializeDB().checkAndInitializeDB();
    }
}
