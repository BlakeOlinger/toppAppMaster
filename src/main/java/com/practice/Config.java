package com.practice;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class Config {
    static String programState = "1";
    static boolean isLiveUpdate = false;
    static ArrayList<Boolean> areServicesInstalled = new ArrayList<>();
    static boolean isDatabaseInstalled = false;
    static ArrayList<Boolean> areAppConfigFilesInitialized = new ArrayList<>();
    static final Path MASTER_CONFIG_PATH =
            Paths.get(Main.userRoot + "programFiles/config/master.config");
}
