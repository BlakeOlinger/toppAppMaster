package com.practice;

import java.io.FileInputStream;
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
            do {
                checkProgramState();

                Thread.sleep(1000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException ignore) {

        }

        new DBProgramState().shutdown();
        new UpdaterProgramState().shutdown();
    }

    private void checkProgramState() {

        FileInputStream configFile;
        try {
            configFile = new FileInputStream(
                    Config.configFilePath + "master.config");

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

            configFile.close();
        } catch (IOException ignore) {

        }
    }
}
