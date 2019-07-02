package com.practice;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

class InitializeDB {

    static boolean checkAndInitializeDB() {

        var databasePath = Paths.get("toppAppDBdaemon/.git");

        if(!Files.exists(databasePath) &&
        hasNetworkConnection()) {
            downloadRemoteDatabase();

            return Files.exists(databasePath);
        } else
            return Files.exists(databasePath);
        }

    static void downloadRemoteDatabase() {
        try {
            var gitCloneProcess = new ProcessBuilder("cmd.exe", "/c", "git", "clone",
                    "https://github.com/BlakeOlinger/toppAppDBdaemon.git").start();
            gitCloneProcess.waitFor();

            gitCloneProcess.destroy();

        } catch (IOException | InterruptedException ignore) {
        }

    }

    static boolean hasNetworkConnection() {
        try {
            return InetAddress.getByName("www.github.com").isReachable(5_000);
        } catch (IOException ignore) {
            return false;
        }
    }

}

