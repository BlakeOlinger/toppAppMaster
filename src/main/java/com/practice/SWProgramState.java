package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

class SWProgramState implements Runnable{
    final Thread thread;

    SWProgramState() {
        thread = new Thread(this, "SW Kill Command");
    }

    @Override
    public void run() {
        var path = Paths.get("programFiles/config/SWmicroservice.config");

        try {
            Files.writeString(path,"11");
        } catch (IOException ignore) {
        }
    }
}
