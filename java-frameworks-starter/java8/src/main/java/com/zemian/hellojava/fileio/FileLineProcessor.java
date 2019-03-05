package com.zemian.hellojava.fileio;

import com.zemian.hellojava.AppException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

public class FileLineProcessor {
    public void eachLine(String fileName, Consumer<String> lineProcessor) {
        try (FileReader reader = new FileReader(fileName)) {
            BufferedReader bReader = new BufferedReader(reader);
            String line;
            while ((line = bReader.readLine()) != null) {
                lineProcessor.accept(line);
            }
        } catch (IOException e) {
            throw new AppException("Failed to read file: " + fileName, e);
        }
    }
}
