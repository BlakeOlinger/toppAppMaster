package com.practice;

import java.io.File;

class AppFileStructure implements Runnable{
    private final Thread thread;

    AppFileStructure() {
        thread = new Thread(this, "File Maker");
    }

    void checkAndInstallFileDirectories() {
        thread.start();
    }

    @Override
    public void run() {
        var configPath = new File("./programFiles/config/");
        var currentVersion = new File("./programFiles/bin/currentVersion/");
        var blempDirectory = new File("./programFiles/blemp/");

        if(!configPath.exists()) {
            configPath.mkdirs();
        }

        if(!currentVersion.exists()) {
            currentVersion.mkdirs();
        }

        if(!blempDirectory.exists()) {
            blempDirectory.mkdirs();
        }
    }
}
