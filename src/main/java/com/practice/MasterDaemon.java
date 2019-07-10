package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

        var countdown = 3;

        try {
            do {

                if (countdown-- == 0) {
                    logger.log(Level.INFO,
                            "Daemon - Checking for Live Update Update Command - Start");

                    checkLiveUpdateCommandState(
                            Config.MASTER_CONFIG_PATH
                    );

                    if (Config.isLiveUpdate) {
                        logger.log(Level.INFO,
                                "Daemon - Checking for Live Update Update Command - Update Found");

                        var liveUpdate = new UpdateLiveUpdate();

                        liveUpdate.update();

                        liveUpdate.join();

                    } else {
                        logger.log(Level.INFO,
                                "Daemon - Checking for Live Update Update Command - No Updates Found");
                    }

                    logger.log(Level.INFO,
                            "Daemon - Checking for Live Update Update Command - Exit");

                    countdown = 3;
                }

                checkProgramState(
                        Config.MASTER_CONFIG_PATH
                );

                Thread.sleep(2000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException e) {
            logger.log(Level.INFO, "Error Daemon Thread Interrupted", e);
        }

        logger.log(Level.INFO, "Daemon - Exit");
    }

    void checkLiveUpdateCommandState(Path masterConfig) {
        try {
            Config.isLiveUpdate =
                    Files.readString(masterConfig)
                            .substring(1, 2).compareTo("0") == 0;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error Reading Master Config File", e);
        }
    }

    private void checkProgramState(Path masterConfig) {
        try {
            Config.programState = Files.readString(masterConfig).substring(0,1);
        } catch (IOException ignore) {
        }
    }
}
