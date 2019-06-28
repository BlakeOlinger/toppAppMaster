package com.practice;

import java.io.FileInputStream;
import java.io.IOException;

class InitializeDB {

    void checkAndInitializeDB() {
        run();
    }

    private void run() {
        try {
            var gitConfig = new FileInputStream("toppAppDBdaemon/.git");
            if(gitConfig.available() == 0)
                throw new IOException();
        } catch (IOException ignore) {
            Config.isDBUninitialized = true;
        }

        try {
            if (Config.isDBUninitialized) {
                var process = Runtime.getRuntime().exec(
                        "cmd.exe /c git clone https://github.com/BlakeOlinger/toppAppDBdaemon.git");

                process.waitFor();

                Config.isDBUninitialized = false;
            }
        } catch (InterruptedException | IOException ignore) {

        }
    }
}
