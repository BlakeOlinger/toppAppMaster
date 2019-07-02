package com.practice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class MicroServicesInstaller implements Runnable{
    private final Thread thread;
    private final Path source;
    private final Path target;

    MicroServicesInstaller(Path source, Path target) {
        this.source = source;
        this.target = target;
        thread = new Thread(this, "Microservice Installer");
    }

    void install() {
         thread.start();
    }

    @Override
    public void run() {
        if(!Files.exists(target)) {
            try {
                Files.copy(source, target);
            } catch (IOException ignore) {
            }
            Config.areServicesInstalled.add(Files.exists(target));
        } else
            Config.areServicesInstalled.add(Boolean.TRUE);
    }
}
