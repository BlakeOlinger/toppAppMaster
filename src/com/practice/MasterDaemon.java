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

        System.out.println(" Master Daemon - Start");
        try {
            do {
                checkProgramState();

                System.out.println(" Master Daemon - Thread Sleep 2,000 ms");
                Thread.sleep(2000);
            } while (Config.programState.compareTo("0") == 0);
        } catch (InterruptedException ignore) {

        }

        new DBProgramState().shutdown();

        new UpdaterProgramState().shutdown();

        new GUIProgramState().shutdown();

        System.out.println(" Master Daemon - End");
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
