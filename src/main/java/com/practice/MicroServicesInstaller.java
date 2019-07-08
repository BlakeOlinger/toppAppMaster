package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

class MicroServicesInstaller implements Runnable{
    private final Thread thread;
    private final Path source;
    private final Path target;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    MicroServicesInstaller(Path source, Path target) {
        this.source = source;
        this.target = target;
        thread = new Thread(this, "Microservice Installer");
    }

    void install() {
         thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Could not Join on " +
                    thread.getName(), e);
        }
    }

    @Override
    public void run() {
        if(!Files.exists(target)) {
            try {
                Files.copy(source, target);
            } catch (IOException ignore) {
            }
            Config.areServicesInstalled.add(Files.exists(target));
        } else
            Config.areServicesInstalled.add(Boolean.TRUE);
    }
}
