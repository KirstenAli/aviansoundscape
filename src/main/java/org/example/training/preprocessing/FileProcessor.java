package org.example.training.preprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileReader {

    private final String directoryPath;

    public FileReader(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    private

    private static byte[] readFileBytes(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readAllBytes(path);
    }
}
