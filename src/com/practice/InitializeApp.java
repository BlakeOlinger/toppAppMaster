package com.practice;

import java.io.FileOutputStream;
import java.io.IOException;

public class InitializeApp implements Runnable{
    private final Thread thread;

    InitializeApp() {
        thread = new Thread(this, "App Config Init");
    }

    void initializeConfigFiles() {
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
        try (var DBconfig = new FileOutputStream(Config.installDirectory + "DBdaemon.config");
             var masterConfig = new FileOutputStream(Config.installDirectory + "master.config");
             var guiConfig = new FileOutputStream(Config.installDirectory + "GUI.config")) {

            char command = '0';

            DBconfig.write((int)command);
            masterConfig.write((int)command);
            guiConfig.write((int)command);

             } catch (IOException ignore) {
        }
    }
}
