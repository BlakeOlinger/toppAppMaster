package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class GUIProgramState implements Runnable{
    final Thread thread;

    GUIProgramState() {
        thread = new Thread(this, "GUI Kill Command");
    }

    @Override
    public void run() {
        var path = Paths.get("programFiles/config/GUI.config");

        try {
            Files.writeString(path,"1");
        } catch (IOException ignore) {
        }
    }
}
