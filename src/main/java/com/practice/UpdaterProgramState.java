package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UpdaterProgramState implements Runnable{
    private final Thread thread;

    UpdaterProgramState() {
        thread = new Thread(this, "Updater Program Kill");
    }

    void shutdown () {
        thread.start();
    }

    @Override
    public void run() {
        var path = Paths.get("programFiles/config/updater.config");

        try {
            Files.writeString(path,"1");
        } catch (IOException ignore) {
        }
    }
}
