package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class InitializeApp implements Runnable{
    private final Thread thread;
    private final Path filePath;
    private final CharSequence commands;

    InitializeApp(Path filePath, CharSequence commands) {
        this.filePath = filePath;
        this.commands = commands;
        thread = new Thread(this, "App Config Init");
    }

    void initializeConfigFile() {
        thread.start();

    }

    @Override
    public void run() {
        try {
            var file = Files.writeString(filePath, commands);
            Config.areAppConfigFilesInitialized.add(Files.exists(file));
        } catch (IOException ignore) {
        }
    }
}
