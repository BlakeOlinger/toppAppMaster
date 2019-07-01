package com.practice;

import java.io.FileOutputStream;
import java.io.IOException;

class GUIProgramState implements Runnable{
    private final Thread thread;

    GUIProgramState() {
        thread = new Thread(this, "GUI Kill Command");
    }

    void shutdown() {
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
        System.out.println(" Sending Kill Command to GUI Microservice");

        var GUIconfigPath = "programFiles/config/GUI.config";

        try (var GUIconfig = new FileOutputStream(GUIconfigPath)) {
            var command = (int) '1';

            GUIconfig.write(command);
        } catch (IOException ignore) {
            System.out.println(" ERROR: Could Not Write GUI Config Command");
        }
    }
}
