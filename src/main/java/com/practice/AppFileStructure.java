package com.practice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        var blempConfig = Paths.get("programFiles/blemp/config.blemp");
        var sourceBlemp = "toppAppDBdaemon/programFiles/blemp/";

        if(!Files.exists(blempConfig)) {
            try {
                Files.copy(Paths.get(sourceBlemp + "config.blemp"), blempConfig);
            } catch (IOException ignore) {
            }
        }
    }
}
