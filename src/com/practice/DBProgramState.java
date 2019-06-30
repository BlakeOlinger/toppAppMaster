package com.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class DBProgramState implements Runnable{
    private final Thread thread;

    DBProgramState() {
        thread = new Thread(this, "DB Daemon Kill");
    }

    void shutdown() {
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

        System.out.println(" Sending Kill Command - Database Microservice");

        try (var DBconfig = new FileOutputStream(Config.configFilePath + "DBdaemon.config");
        var DBconfigRead = new FileInputStream(Config.configFilePath + "DBdaemon.config")) {
            char command = '1';

            DBconfig.write((int) command);
            DBconfig.write((int) command);

            int byteRead = DBconfigRead.read();

            if(String.valueOf((char) byteRead).compareTo("1") == 0) {
                System.out.println(" Database Microservice Config File Updated with Kill Command - Success");
            } else {
                System.out.println(" ERROR: Could Not Send Database Microservice Kill Command");
            }

        } catch (IOException ignore) {
        }


    }
}
