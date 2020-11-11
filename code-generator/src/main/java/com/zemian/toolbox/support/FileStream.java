package com.zemian.toolbox.support;

import java.io.InputStream;

public class FileStream {
    private String path;
    private InputStream stream;

    public FileStream(String path, InputStream stream) {
        this.path = path;
        this.stream = stream;
    }

    public String getPath() {
        return path;
    }

    public InputStream getStream() {
        return stream;
    }

    @Override
    public String toString() {
        return "FileStream{" +
                "path='" + path + '\'' +
                '}';
    }
}
