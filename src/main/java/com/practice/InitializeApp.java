package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitializeApp implements Runnable{
    private final Thread thread;
    private final Path filePath;
    private final CharSequence commands;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    InitializeApp(Path filePath, CharSequence commands) {
        this.filePath = filePath;
        this.commands = commands;
        thread = new Thread(this, "App Config Init");
    }

    void initializeConfigFile() {
        thread.start();

    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Waiting for Join - " +
                    thread.getName(), e);
        }
    }

    @Override
    public void run() {
        try {
            var file = Files.writeString(filePath, commands);
            Config.areAppConfigFilesInitialized.add(Files.exists(file));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Initializing Config Files", e);
        }
    }
}
