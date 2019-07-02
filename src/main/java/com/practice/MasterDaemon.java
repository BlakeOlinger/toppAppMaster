package com.practice;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class MasterDaemon implements Runnable{
    private final Thread thread;

    MasterDaemon() {
        thread = new Thread(this,"Master Daemon");
    }

    void startMasterDaemon() {
        thread.start();
    }


    @Override
    public void run() {

        try {
            do {

                checkLiveUpdateCommandState();

                if(Config.liveUpdateCommandState.compareTo("0") == 0) {
                    new UpdateLiveUpdate().update();
                }

                checkProgramState();

                Thread.sleep(2000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException ignore) {

        }

        new DBProgramState().shutdown();

        new UpdaterProgramState().shutdown();

        new GUIProgramState().shutdown();

        // new SWdaemonState().shutdown();
    }

    private void checkLiveUpdateCommandState() {
        var path = Paths.get("programFiles/config/master.config");

        try {
            Config.liveUpdateCommandState = Files.readString(path).substring(1, 1);
        } catch (IOException ignore) {
        }
    }

    private void checkProgramState() {

        try (var configFile = new FileInputStream( "master.config")){
            int readByte;
            int index = 0;
            do {
                readByte = configFile.read();

                if (readByte != -1) {
                    for (; index < 1; ++index) {
                        Config.programState = String.valueOf((char) readByte);
                    }
                }

            } while (readByte != -1);

        } catch (IOException ignore) {

        }
    }
}
