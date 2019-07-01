package com.practice;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InitializeDBTest {

    @Test
    void givenTEST_DIRreturnTrue() throws IOException {

        var TEST_DIR = Files.createTempDirectory("testDir");
        TEST_DIR = Files.createTempFile(TEST_DIR, "test", "file");

        assertTrue(InitializeDB.hasLocalDatabase(TEST_DIR));
    }

    @Test
    void givenNoneExistentFileAndDirectoryReturnFalse(){
        var fakePath = Paths.get("non-existent-file");

        assertFalse(InitializeDB.hasLocalDatabase(fakePath));
    }

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

    private static boolean deleteDirectory(File directory) {
        File[] files = directory.listFiles();
        if (files != null) {

            for(File file: files)
                deleteDirectory(file);
        }

        return directory.delete();
    }
}