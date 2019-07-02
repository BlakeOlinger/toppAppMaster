package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class DBProgramState implements Runnable{
    private final Thread thread;

    DBProgramState() {
        thread = new Thread(this, "DB Daemon Kill");
    }

    void shutdown() {
        thread.start();
    }

    @Override
    public void run() {
        var path = Paths.get("programFiles/config/DBdaemon.config");

        try {
            Files.writeString(path,"11");
        } catch (IOException ignore) {
        }
    }
}
