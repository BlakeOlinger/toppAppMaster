package com.practice;

import java.io.FileOutputStream;
import java.io.IOException;

class SWdaemonState implements Runnable{
    private final Thread thread;

    SWdaemonState() {
        thread = new Thread(this, "SW Daemon Kill");
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
        System.out.println(" Sending Kill Command to SW Daemon");
        var command = (int) '1';
        var SWconfigPath = "programfiles/config/SWdaemon.config";

        try (var SWconfig = new FileOutputStream(SWconfigPath)) {
            SWconfig.write(command);

        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Write Kill Command");
        }
    }

    void shutdown() {
        thread.start();
    }
}
