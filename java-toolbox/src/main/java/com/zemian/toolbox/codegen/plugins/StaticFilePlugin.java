package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.codegen.TemplateContext;
import com.zemian.toolbox.support.FileStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class StaticFilePlugin extends AbstractPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(StaticFilePlugin.class);
    public static final String STATIC_PREFIX = "static";

    @Override
    public String getHelp() {
        return "Copy all static files from template set folder to output directory.\n" +
                "\n" +
                "NOTE: This plugin always enabled.\n" +
                "  --staticDirectory=DIR\n" +
                "      The name of the directory to copy static files.\n" +
                "      Default '<templateSetName>/static'\n" +
                "";
    }

    @Override
    public void generate(TemplateContext templateContext) throws Exception {
        FileStream templateFileStream = templateContext.getTemplateFileStream();
        String templatePath = templateFileStream.getPath();
        if (!templatePath.startsWith(STATIC_PREFIX)) {
            return;
        }

        String outputPath = templateFileStream.getPath().substring(STATIC_PREFIX.length() + 1);
        File outputFile = new File(codeGen.getOutputDir(), outputPath);

        // Ensure output file and dir is ready for write new file.
        if (!codeGen.isOverrideFiles() && outputFile.exists()) {
            return;
        }

        LOG.info("Generating static file from {} to {}", templatePath, outputFile);
        FileUtils.copyInputStreamToFile(templateContext.getTemplateFileStream().getStream(), outputFile);
    }
}
