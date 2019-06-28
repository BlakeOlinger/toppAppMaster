package com.practice;

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
        // TODO get to write to file - won't for some reason - make DB daemon check more often for kill
        // decouple check for kill from check for pull
        // TODO  - change non DB files from toppAppDBdaemon to  /programfiles/config and
        // /programfiles/session - for .config and .session files
        // TODO pathing off in DB daemon - created multiple .git repos
        // TODO have master boot strap app with installer function - master will have shortcut icon
        // move DB initializer from DB daemon to master and master will copy .jars and run them
        // make shortcut on desktop as well
        // TODO - restructure modules/packages so all apps are in one package for easy JLinking
        // TODO - JLink all so each service is a standalone entity
        // TODO - have master listen for udpate service command to update the updater service
        // TODO - change any unnecessary calls to cmd.exe to Java runtime commands
        // TODO - include .jar/.bat in DB /bin folder
        // TODO - decouple DB daemon source code repository from DB repository
        // TODO - in addition to an installer - make an uninstaller via some
        // manual command input in the master.config file - make it delete all
        // directories and files related to app
        // TODO - either for GUI or internally allow a user to input a general area
        // for a new feature - I, II, III, IV - centered in each quadrant
        // and I-IV, II-III, III-IV, IV-I for centered on the right/left, up/down axis
        // TODO - master.installer in initializing DB is going to check for git command
        // functionality and git repo - if not install - installer ships with master
        // configure PATH for git and then initialize repo
        // TODO - make tool to copy .jar/.bat from restricted dev out folder to TOPP App DB update folder
        // add that tool to PATH so a simple command will execute it

        // For Debugging
        /*
        System.out.println("Shutdown");

        var file = new File(Config.installDirectory + "DBdaemon.config");
        System.out.println(file.exists());
        */
        try (var DBconfig = new FileOutputStream(Config.installDirectory + "DBdaemon.config")) {
            char command = '1';

            DBconfig.write((int) command);
        } catch (IOException ignore) {
        }


    }
}
