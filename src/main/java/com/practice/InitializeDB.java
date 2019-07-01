package com.practice;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// TODO - instead of console out or file logs - utilize full unit and integration tests
class InitializeDB {

    static void checkAndInitializeDB() {

        var databasePath = Paths.get("toppAppDBdaemon/.git");

        if(!hasLocalDatabase(databasePath) &&
        hasNetworkConnection()) {
            downloadRemoteDatabase();
        }

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

    static boolean hasLocalDatabase(Path databasePath) {
        return Files.exists(databasePath);
    }

}

