package com.practice;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MicroServicesInstallerTest {

    @Test
    void returnTrueIfFileInstalledFromNot() throws IOException {
        var testSourceFile = Files.createTempFile("test", "source");
        var testTargetFile = Files.createTempFile("test", "target");

        assertTrue(MicroServicesInstaller.install(testSourceFile, testTargetFile));

        Files.delete(testSourceFile);
        Files.delete(testTargetFile);

        assertFalse(Files.exists(testSourceFile));
        assertFalse(Files.exists(testTargetFile));
    }

    @Test
    void returnTrueForAllInPathArrayInstalled() throws IOException {
        var sources = new Path[]{
                Files.createTempFile("test", "sourceOne"),
                Files.createTempFile("test", "sourceTwo")
        };

        var targets = new Path[] {
                Files.createTempFile("test", "targetOne"),
                Files.createTempFile("test", "targetTwo")
        };

        assertTrue(MicroServicesInstaller.checkAndInstallService(sources, targets));

        for(Path file: sources) {
            Files.delete(file);
            assertFalse(Files.exists(file));
        }

        for(Path file: targets) {
            Files.delete(file);
            assertFalse(Files.exists(file));
        }
    }
}