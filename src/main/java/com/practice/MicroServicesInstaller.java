package com.practice;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

class MicroServicesInstaller {

     static boolean checkAndInstallServices(@NotNull Path[] sources, @NotNull Path[] targets) {
         if(sources.length == targets.length) {
             var installedList = new ArrayList<Boolean>();

             for(var i = 0; i < sources.length; ++i) {
                 installedList.add(install(sources[i], targets[i]));
             }

             return !installedList.contains(Boolean.FALSE);
         } else
             return false;

    }

    static boolean install(Path source, Path target) {
         if(!Files.exists(target)) {
             try {
                 Files.copy(source, target);
             } catch (IOException ignore) {
             }
             return Files.exists(target);
         } else
             return true;
    }

}
