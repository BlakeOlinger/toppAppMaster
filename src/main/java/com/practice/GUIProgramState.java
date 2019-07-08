package com.practice;

import java.nio.file.Path;

class GUIProgramState extends DBProgramState{

    GUIProgramState(Path path) {
        super(path);
    }

    @Override
    void shutdown() {
        super.shutdown();
    }

    @Override
    void join() {
        super.join();
    }

    @Override
    public void run() {
        super.run();
    }

    @Override
    void checkDBState() {
        super.checkDBState();
    }

    @Override
    String getState() {
        return super.getState();
    }
}
