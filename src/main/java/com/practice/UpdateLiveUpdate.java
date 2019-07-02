package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

class UpdateLiveUpdate implements Runnable {
    private final Thread thread;

    UpdateLiveUpdate() {
        thread = new Thread(this, "Update Live Update Microservice");
    }

    void update() {
        thread.start();
    }

    @Override
    public void run() {
        new UpdaterProgramState().shutdown();

        var targetOne = Paths.get("programFiles/bin/currentVersion/toppAppUpdater.jar");
        var targetTwo = Paths.get("toppAppUpdater.jar");
        var source = Paths.get("toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar");

        try {
            Files.copy(source, targetOne, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(source, targetTwo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignore) {
        }

        var liveUpdateConfigPath = Paths.get("programFiles/config/updater.config");

        try {
            Files.writeString(liveUpdateConfigPath, new StringBuilder("0"));
        } catch (IOException ignore) {
        }

        new App("toppAppUpdater.jar").startMicroservice();

        var masterConfigPath = Paths.get("programFiles/config/master.config");

        try {
            Files.writeString(masterConfigPath, new StringBuilder("01"));
        } catch (IOException ignore) {
        }


    }
}
