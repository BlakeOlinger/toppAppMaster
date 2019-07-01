package com.practice;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class MasterDaemon implements Runnable{
    private final Thread thread;

    MasterDaemon() {
        thread = new Thread(this,"master daemon");
    }

    void startMasterDaemon() {
        thread.start();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>checkAndInitializeDB</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>checkAndInitializeDB</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        System.out.println(" Master Daemon - Start");
        try {
            do {

                checkLiveUpdateCommandState();

                if(Config.liveUpdateCommandState.compareTo("0") == 0) {
                    updateLiveUpdateMicroservice();
                }

                checkProgramState();

                System.out.println(" Master Daemon - Thread Sleep 2,000 ms");
                Thread.sleep(2000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException ignore) {

        }

        new DBProgramState().shutdown();

        new UpdaterProgramState().shutdown();

        new GUIProgramState().shutdown();

        new SWdaemonState().shutdown();

        System.out.println(" Master Daemon - End");
    }

    private void updateLiveUpdateMicroservice() {
        System.out.println(" Master Daemon - Updating Live Update Microservice");

        new UpdaterProgramState().shutdown();

        System.out.println(" Installing Latest Live Update Version");

        var currentPath = "programFiles/bin/currentVersion/toppAppUpdater.jar";
        var latestPath = "toppAppDBdaemon/programFiles/bin/toppAppUpdater.jar";

        try (var latest = new FileInputStream(latestPath);
        var current = new FileOutputStream(currentPath);
        var binInstall = new FileOutputStream("toppAppUpdater.jar")) {
            int readByte;

            do {
                readByte = latest.read();
                if(readByte != -1) {
                    current.write(readByte);
                    binInstall.write(readByte);
                }

            } while (readByte != -1);

            System.out.println(" Master Daemon - Live Update New Version Installed");
        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Install Files");
        }

        var liveUpdateConfigPath = "programFiles/config/updater.config";

        System.out.println(" Master Daemon - Sending Start Command - Live Update Microservice");

        try (var updaterConfig = new FileOutputStream(liveUpdateConfigPath)) {
            var command = (int) '0';

            updaterConfig.write(command);

        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Send Start Command to Live Update Microservice");
        }

        try {
            Runtime.getRuntime().exec(" cmd.exe /c toppAppUpdater.jar");
        } catch (IOException ignore) {
        }

        var masterConfigPath = "programFiles/config/master.config";

        try(var masterConfig = new FileOutputStream(masterConfigPath)) {
            var commandOne = (int) '0';
            var commandTwo = (int) '1';

            masterConfig.write(commandOne);
            masterConfig.write(commandTwo);

        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Write to Master Config File");
        }

    }

    private void checkLiveUpdateCommandState() {
        System.out.println(" Master Microservice - Reading Live Update Command State");

        var configPath = "programFiles/config/master.config";

        try (var masterConfig = new FileInputStream(configPath)){
            masterConfig.read();

            Config.liveUpdateCommandState = String.valueOf((char) masterConfig.read());

            System.out.println(" Master Daemon - Live Update Command State - " + Config.liveUpdateCommandState);

        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Read Master Config File");
        }
    }

    private void checkProgramState() {

        try (var configFile = new FileInputStream(Config.configFilePath + "master.config")){
            System.out.println(" Master Daemon - Reading Program State...");
            int readByte;
            int index = 0;
            do {
                readByte = configFile.read();

                if (readByte != -1) {
                    for (; index < 1; ++index) {
                        Config.programState = String.valueOf((char) readByte);
                    }
                }

                System.out.println(" Master Daemon Program State - " + Config.programState );


            } while (readByte != -1);

        } catch (IOException ignore) {

        }
    }
}
