package com.practice;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

class InitializeDBTest {

    @Test
    void returnTrueIfConnectedToInternetAndCanAccessDatabase() {
        assertTrue(InitializeDB.hasNetworkConnection());
    }

    @Test
    void assertThatDatabaseDirectoryExists() {
        InitializeDB.downloadRemoteDatabase();

        var testDirectory = new File("toppAppDBdaemon");

        assertTrue(testDirectory.exists());

       assertTrue(deleteDirectory(testDirectory));
    }

    private static boolean deleteDirectory(@NotNull File directory) {
        File[] files = directory.listFiles();
        if (files != null) {

            for(File file: files)
                deleteDirectory(file);
        }

        return directory.delete();
    }
}