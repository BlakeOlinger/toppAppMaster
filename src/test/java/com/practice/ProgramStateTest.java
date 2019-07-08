package com.practice;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProgramStateTest {
    private final static Path path = Paths.get("test.txt");

    @BeforeAll
    static void setUp() throws IOException {
        Files.writeString(path, "01");
    }

    @AfterAll
    static void tearDown() throws IOException {
        if (Files.exists(path))
            Files.delete(path);
    }

    @Test
    void return_0_for_01() {
        var dbTest = new ProgramState(path, "testService");
        dbTest.checkDBState();

        var result = dbTest.getState();

        assertEquals("0", result);
    }
}