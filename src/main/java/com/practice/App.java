package com.practice;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

class App implements Runnable{
    private final Thread thread;
    private final String name;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    App(String name) {
        this.name = name;
        thread = new Thread(this, "Start Microservice");
    }

    void startMicroservice() {
        thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Could Not Join on " +
                    thread.getName(), e);
        }
    }

    @Override
    public void run() {
        try {
            var process = new ProcessBuilder("cmd.exe", "/c", name).start();
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException ignore) {
        }
    }
}
