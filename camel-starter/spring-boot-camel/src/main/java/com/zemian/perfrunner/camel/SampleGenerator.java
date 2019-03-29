package com.zemian.perfrunner.camel;

import java.io.*;
import java.nio.file.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SampleGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(SampleGenerator.class);
	
	public static void main(String[] args) throws Exception {
		File sampleDir = new File(System.getProperty("sampleDir", "target/samples"));
		File moveToDir = new File(System.getProperty("moveToDir", "target/file-poller"));
		int size = Integer.parseInt(System.getProperty("size", "1"));
		sampleDir.mkdirs();
		moveToDir.mkdirs();
		for (int i = 0; i < size; i++) {
			String name = "sample" + i + ".txt";
			File file = new File(sampleDir, name);
			LOG.debug("Generating sample file {}", file);
			try (FileWriter out = new FileWriter(file)) {
				out.write("Test\n");
			}
		}

		for (int i = 0; i < size; i++) {
			String name = "sample" + i + ".txt";
			Path p1 = Paths.get(sampleDir.getPath(), name);
			Path p2 = Paths.get(moveToDir.getPath(), name);
			LOG.debug("Moving file {} to {}", p1, p2);
			Files.move(p1, p2, StandardCopyOption.REPLACE_EXISTING);
		}
	}
}
