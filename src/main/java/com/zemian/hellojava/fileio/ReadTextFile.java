package com.zemian.hellojava.fileio;

import com.zemian.hellojava.AppException;

import java.io.FileReader;
import java.io.IOException;

public class ReadTextFile {
    public String readFile(String fileName) {
        StringBuffer result = new StringBuffer();
        try (FileReader reader = new FileReader(fileName)) {
            int blockSize = 5 * 1024;
            char[] buff = new char[blockSize];
            int len;
            while ((len = reader.read(buff, 0, blockSize)) != -1) {
                result.append(buff, 0, len);
            }
        } catch (IOException e) {
            throw new AppException("Failed to read file: " + fileName, e);
        }
        return result.toString();
    }
}
