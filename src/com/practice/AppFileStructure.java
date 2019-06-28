package com.practice;

import java.io.File;
import java.io.IOException;

class AppFileStructure implements Runnable{
    private final Thread thread;

    AppFileStructure() {
        thread = new Thread(this, "File Maker");
    }

    void checkAndInstallFileDirectories() {
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
        var directoryPath = new File("/programFiles/config/");



        if(!directoryPath.exists()) {

            // have to use cmd.exe in windows
            // try Java File class
            try {
                Runtime.getRuntime().exec("cmd.exe /c ");
            } catch (IOException ignore) {
            }
        }
    }
}
