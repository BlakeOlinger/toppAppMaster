package com.practice;

import java.io.IOException;

class App implements Runnable{
    private final Thread thread;
    private final String name;

    App(String name) {
        this.name = name;
        thread = new Thread(this, "Start Microservice");
    }

    void startMicroservice() {
        thread.start();
    }

    @Override
    public void run() {
        try {
            var process = new ProcessBuilder("cmd.exe", "/c", name).start();
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException ignore) {
        }
    }
}
