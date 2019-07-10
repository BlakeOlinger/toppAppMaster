package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

class ProgramState implements Runnable{
    final Thread thread;
    private final Path path;
    private String state = "1";
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private final String serviceName;

    ProgramState(Path path, String serviceName) {
        this.path = path;
        this.serviceName = serviceName;
        thread = new Thread(this, "DB Daemon Kill");
    }

    void shutdown() {
        thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error" +
                    serviceName +" Shutdown Join", e);
        }
    }

    @Override
    public void run() {
        logger.log(Level.INFO, serviceName + " Shutdown - Start");

        do {
          try {
                Files.writeString(path, "111");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error Writing " +
                        serviceName + " Config File");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Error " +
                        thread.getName() + " Interrupted", e);
            }

            checkDBState();

        } while (state.compareTo("0") == 0);

        logger.log(Level.INFO, serviceName + " Shutdown - Exit");
    }

    void checkDBState() {
        try {
            state = Files.readString(path).substring(0, 1);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Reading " +
                    serviceName + " Config File", e);
        }
    }

    String getState() {
        return state;
    }
}
