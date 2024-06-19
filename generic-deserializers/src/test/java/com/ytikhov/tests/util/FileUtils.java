package com.ytikhov.tests.util;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    public static String readFromResources(String filename) {
        URL resource = FileUtils.class.getClassLoader().getResource(filename);
        String absolutePath = resource.getFile();

        File file = new File(absolutePath);
        try(FileInputStream fis = new FileInputStream(file)) {
            byte[] bytes = fis.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
