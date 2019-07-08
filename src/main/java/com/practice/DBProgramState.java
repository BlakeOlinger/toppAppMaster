package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

// TODO - all shutdown actions can stem from this class
//  - no need to have a separate class - just make
//  separate instances of this - modify by allowing
//  - a message parameter to indicate different services
class DBProgramState implements Runnable{
    final Thread thread;
    private final Path path;
    private String state = "1";
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    DBProgramState(Path path) {
        this.path = path;
        thread = new Thread(this, "DB Daemon Kill");
    }

    void shutdown() {
        thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Database Shutdown Join", e);
        }
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Database Shutdown - Start");

        do {
          try {
                Files.writeString(path, "11");
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error Writing Database Config File");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, "Error " +
                        thread.getName() + " Interrupted", e);
            }

            checkDBState();

        } while (state.compareTo("0") == 0);

        logger.log(Level.INFO, "Database Shutdown - Exit");
    }

    void checkDBState() {
        try {
            state = Files.readString(path).substring(0, 1);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Reading DB Config File", e);
        }
    }

    String getState() {
        return state;
    }
}
