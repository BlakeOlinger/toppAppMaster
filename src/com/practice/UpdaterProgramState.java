package com.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdaterProgramState implements Runnable{
    private final Thread thread;

    UpdaterProgramState() {
        thread = new Thread(this, "Updater Program Kill");
    }

    void shutdown () {
        thread.start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        System.out.println(" Sending Kill Command - Live Update Microservice");
        try (var updaterConfig = new FileOutputStream(Config.configFilePath + "updater.config");
        var configRead = new FileInputStream(Config.configFilePath + "updater.config")) {
            char command = '1';

            updaterConfig.write((int) command);

            int byteRead = configRead.read();
            if (String.valueOf((char) byteRead).compareTo("1") == 0) {
                System.out.println(" Live Update Microservice Kill Command - Success");
            } else {
                System.out.println(" ERROR: Could Not Send Live Update Microservice Kill Command");
            }

        } catch (IOException ignore) {
        }

    }
}
