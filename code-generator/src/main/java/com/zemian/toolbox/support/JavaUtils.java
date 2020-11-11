package com.zemian.toolbox.support;

import com.zemian.toolbox.AppException;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class JavaUtils {
    public static Map<String, Object> map(Object ... pairs) {
        Map<String, Object> ret = new HashMap<>();
        for (int i = 0; i < pairs.length; i+=2) {
            ret.put(pairs[i].toString(), pairs[i+1]);
        }
        return ret;
    }

    public static String underscoreToCamel(String underscoreName) {
        String[] parts = underscoreName.split("_");
        String ret = Arrays.stream(parts).map(w -> upCase(w)).collect(Collectors.joining());
        return lowCase(ret);
    }

    /* Up case first letter of the word. */
    public static String upCase(String word) {
        if (word.length() > 0) {
            return ("" + word.charAt(0)).toUpperCase() + word.substring(1);
        }
        return word;
    }

    /* Low case first letter of the word. */
    public static String lowCase(String word) {
        if (word.length() > 0) {
            return ("" + word.charAt(0)).toLowerCase() + word.substring(1);
        }
        return word;
    }

    public static String camelToUnderscore(String camel) {
        int lastWordIdx = 0;
        List<String> parts = new ArrayList<>();
        for (int i = 0; i < camel.length(); i++) {
            if (StringUtils.isAllUpperCase(camel.substring(i, i + 1))) {
                parts.add(camel.substring(lastWordIdx, i).toLowerCase());
                lastWordIdx = i;
            }
        }
        parts.add(camel.substring(lastWordIdx).toLowerCase());
        return StringUtils.join(parts, "_");
    }

    /** Convert a hyphen separated words into a Java package. */
    public static String urlNameToPackage(String urlName) {
        String[] parts = urlName.split("(\\-|_|\\.)");
        return StringUtils.join(parts, ".");
    }

    public static void walkClasspathResDir(ClassLoader cl, String resDir, ConsumerEx<FileStream> fileConsumer) {
        // NOTE: JarEntry.isDirectory() used below requires input to ends with slash.
        if (!resDir.endsWith("/")) {
            resDir = resDir + "/";
        }

        URL url = cl.getResource(resDir);
        if (url == null) {
            throw new AppException("Resource directory " + resDir + " not found in classpath.");
        } else if ("file".equals(url.getProtocol())) {
            File dir = new File(url.getPath());
            if (!dir.isDirectory()) {
                throw new AppException("Resource is not a directory: " + resDir);
            }
            walkFileDir(dir, fileConsumer);
        } else if ("jar".equals(url.getProtocol())) {
            try {
                JarURLConnection conn = (JarURLConnection) url.openConnection();
                if (!(conn.getJarEntry().isDirectory())) {
                    throw new AppException("Resource is not a directory: " + resDir);
                }
                walkJarEntryDir(conn.getJarFile(), resDir, fileConsumer);
            } catch (Exception e) {
                throw new AppException("Unable to read resource: " + url, e);
            }
        } else {
            throw new AppException("Unsupported protocol " + url.getProtocol() + " for checking resource directory.");
        }
    }

    public static void walkFileDir(File dir, ConsumerEx<FileStream> fileConsumer) {
        try {
            Files.walkFileTree(dir.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try (FileInputStream stream = new FileInputStream(file.toFile())) {
                        try {
                            String shortPath = file.toFile().getPath().substring(dir.getPath().length());
                            // Always convert path separator to Unix
                            shortPath = FilenameUtils.separatorsToUnix(shortPath);
                            // Remove leading '/' if there is any
                            if (shortPath.startsWith("/")) {
                                shortPath = shortPath.substring(1);
                            }
                            fileConsumer.accept(new FileStream(shortPath, stream));
                        } catch (Exception e) {
                            throw new AppException("Failed to process file: " + file, e);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AppException("Failed to walk directory: " + dir, e);
        }
    }

    public static void walkJarEntryDir(JarFile jarFile, String entryName, ConsumerEx<FileStream> fileConsumer) {
        try {
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                if (jarEntry.getName().startsWith(entryName)) {
                    if (!jarEntry.isDirectory()) {
                        try (InputStream stream = jarFile.getInputStream(jarEntry)) {
                            try {
                                String shortPath = jarEntry.getName().substring(entryName.length());
                                fileConsumer.accept(new FileStream(shortPath, stream));
                            } catch (Exception e) {
                                throw new AppException("Failed to process jar entry: " + jarEntry.getName(), e);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new AppException("Failed to walk jar directory entry: " + entryName, e);
        }
    }
}
