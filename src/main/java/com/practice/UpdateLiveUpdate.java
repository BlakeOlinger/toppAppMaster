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

    void join() {
        try {
            thread.join();
        } catch (InterruptedException ignore) {
        }
    }

    @Override
    public void run() {
        var killLiveUpdate = new UpdaterProgramState();

        killLiveUpdate.shutdown();

        killLiveUpdate.join();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException ignore) {
        }

        var targetOne = Paths.get(Main.userRoot
                + "programFiles/bin/currentVersion/toppAppUpdater.jar");
        var targetTwo = Paths.get(Main.userRoot
                + "toppAppUpdater.jar");
        var source = Paths.get(Main.userRoot
                + "toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar");

        try {
            Files.copy(source, targetOne, StandardCopyOption.REPLACE_EXISTING);
            Files.copy(source, targetTwo, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ignore) {
        }

        var liveUpdateConfigPath = Paths.get(Main.userRoot
                + "programFiles/config/updater.config");

        try {
            Files.writeString(liveUpdateConfigPath,"000");
        } catch (IOException ignore) {
        }

        var updaterStartBat = Main.userRoot
                + "toppAppDBdaemon/programFiles/bat/toppAppUpdater.bat";

        try {

            var process = new ProcessBuilder("cmd.exe", "/c",
                    updaterStartBat).start();

            process.waitFor();

            process.destroy();

        } catch (IOException | InterruptedException ignore) {
        }

        var masterConfigPath = Paths.get(Main.userRoot
                + "programFiles/config/master.config");

        try {
            Files.writeString(masterConfigPath,"011");
        } catch (IOException ignore) {
        }


    }
}
