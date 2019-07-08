package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

class MasterDaemon implements Runnable{
    private final Thread thread;
    private static final Logger logger =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    MasterDaemon() {
        thread = new Thread(this,"Master Daemon");
    }

    void startMasterDaemon() {
        thread.start();
    }

    void join() {
        try {
            thread.join();
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Error Thread " +
                    thread.getName() + " Could not Join", e);
        }
    }


    @Override
    public void run() {
        logger.log(Level.INFO, "Daemon - Start");

        try {
            do {

                checkLiveUpdateCommandState();

                // Live Update Microservice Updater
//                if(Config.liveUpdateCommandState.compareTo("0") == 0) {
//                    new UpdateLiveUpdate().update();
//                }

                checkProgramState();

                Thread.sleep(2000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException e) {
            logger.log(Level.INFO, "Error Daemon Thread Interrupted", e);
        }

        logger.log(Level.INFO, "Daemon - Exit");
    }

    private void checkLiveUpdateCommandState() {
        var path = Paths.get(Main.userRoot + "programFiles/config/master.config");

        try {
            Config.liveUpdateCommandState = Files.readString(path).substring(1, 2);
        } catch (IOException ignore) {
        }
    }

    private void checkProgramState() {
        var path = Paths.get(Main.userRoot +"programFiles/config/master.config");

        try {
            Config.programState = Files.readString(path).substring(0,1);
        } catch (IOException ignore) {
        }
    }
}
