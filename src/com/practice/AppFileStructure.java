package com.practice;

import java.io.File;

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
        var configPath = new File("./programFiles/config/");
        var currentVersion = new File("./programFiles/bin/currentVersion/");

        if(!configPath.exists()) {

            if(configPath.mkdirs()) {
                System.out.println("Dir Made");
            }
        }

        if(!currentVersion.exists()) {
            if(currentVersion.mkdirs()) {
                System.out.println("Current Version Directory Made");
            }
        }
    }
}
