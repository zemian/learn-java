package com.zemian.hellojava.fileio;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class FilesAndPathTest {
    @Test
    public void test() throws Exception {
        Path tempFile = Files.createTempFile("test", ".txt");
        try {
            String fileName = tempFile.toFile().getName();
            assertThat(fileName, startsWith("test"));
            assertThat(fileName, endsWith(".txt"));
            long size = Files.size(tempFile);
            assertThat(size, is(0L));
        } finally {
            if (tempFile != null)
                Files.delete(tempFile);
        }
    }
}
