package com.zemian.toolbox.codegen;

import com.zemian.toolbox.support.FileStream;

public class TemplateContext {
    private FileStream templateFileStream;

    public FileStream getTemplateFileStream() {
        return templateFileStream;
    }

    public void setTemplateFileStream(FileStream templateFileStream) {
        this.templateFileStream = templateFileStream;
    }

    @Override
    public String toString() {
        return "TemplateContext{" +
                "templateFileStream=" + templateFileStream +
                '}';
    }
}
