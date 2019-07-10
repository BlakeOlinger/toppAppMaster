package com.practice;

import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MasterDaemonTest {
    private static Path testFile00;

    @BeforeAll
    static void setUp() throws IOException {
        testFile00 = Files.createFile(
                Paths.get("testFile00.txt")
        );

        Files.writeString(testFile00, "00");
    }

    @AfterAll
    static void tearDown() throws IOException {
        if (Files.exists(testFile00))
            Files.delete(testFile00);
    }

    @Test
    void return_true_for_file_00() {
        var daemon = new MasterDaemon();

        daemon.checkLiveUpdateCommandState(testFile00);

        assertTrue(Config.isLiveUpdate);
    }
}