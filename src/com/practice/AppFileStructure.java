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

        System.out.println(" Checking for existing File Directories");
        if(!configPath.exists()) {
            System.out.println(" Program Directories Not Found - Creating Directories");

            if(!currentVersion.exists()) {
                currentVersion.mkdirs();
            }

            configPath.mkdirs();

            if(configPath.exists() && currentVersion.exists()) {
                System.out.println(" Directories Successfully Made");
            } else {
                System.out.println(" Unable to Create Directories");
                ++Config.errorCount;
            }
        } else {
            System.out.println(" Program Directories Found");
        }

    }
}
