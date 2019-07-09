package com.practice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

class AppFileStructure implements Runnable{
    private final Thread thread;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    AppFileStructure() {
        thread = new Thread(this, "File Maker");
    }

    void checkAndInstallFileDirectories() {
        thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error waiting for " +
                    thread.getName() + " to Join", e);
        }
    }

    @Override
    public void run() {
        var configPath = new File(Main.userRoot + "programFiles/config/");
        var currentVersion = new File(Main.userRoot + "programFiles/bin/currentVersion/");
        var blempDirectory = new File(Main.userRoot + "programFiles/blemp/");

        if(!configPath.exists()) {
            configPath.mkdirs();
        }

        if(!currentVersion.exists()) {
            currentVersion.mkdirs();
        }

        if(!blempDirectory.exists()) {
            blempDirectory.mkdirs();
        }

        var blempConfig = Paths.get(Main.userRoot + "programFiles/blemp/config.blemp");
        var sourceBlemp = Main.userRoot + "toppAppDBdaemon/programFiles/blemp/";

        if(!Files.exists(blempConfig)) {
            try {
                Files.copy(Paths.get(sourceBlemp + "config.blemp"), blempConfig);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Could not copy DB blemp to local", e);
            }
        }
    }
}
