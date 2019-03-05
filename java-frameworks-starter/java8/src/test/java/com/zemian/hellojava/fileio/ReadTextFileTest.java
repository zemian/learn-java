package com.zemian.hellojava.fileio;

import org.junit.Test;

import java.io.File;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class ReadTextFileTest {
    @Test
    public void readFile() {
        WriteTextFile writer = new WriteTextFile();
        writer.writeFile("target/test.txt", "Hello");

        ReadTextFile reader = new ReadTextFile();
        String text = reader.readFile("target/test.txt");

        assertThat(text, is("Hello"));
    }
}
