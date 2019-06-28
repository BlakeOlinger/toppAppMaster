package com.practice;

import java.io.IOException;

class DBProgramState implements Runnable{
    private final Thread thread;

    DBProgramState() {
        thread = new Thread();
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
        try {
            Runtime.getRuntime().exec("cmd.exe /c echo 1 > /toppAppDBdaemon/config/DBDaemon.config");
        } catch (IOException ignore) {
        }
    }
}
