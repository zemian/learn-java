package com.zemian.toolbox.codegen.plugins;

import com.zemian.toolbox.codegen.TemplateContext;
import com.zemian.toolbox.support.FileStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class FtlTemplatePlugin extends AbstractPlugin {
    private static final Logger LOG = LoggerFactory.getLogger(FtlTemplatePlugin.class);
    public static final String FTL_PREFIX = "ftl";

    @Override
    public String getHelp() {
        return "Copy FreeMarker template files from a template set folder to output directory.\n" +
                "This provide a quick way to duplicate a set of directories and files into output directory, and\n" +
                "yet still provide a miminal basic models to all templates.\n" +
                "\n" +
                "NOTE: This plugin always enabled.\n" +
                "  --ftlDirectory=DIR\n" +
                "      The name of the directory to copy template files.\n" +
                "      Default '<templateSetName>/ftl'\n" +
                "";
    }

    @Override
    public void generate(TemplateContext templateContext) throws Exception {
        FileStream templateFileStream = templateContext.getTemplateFileStream();
        String templatePath = templateFileStream.getPath();
        if (!templatePath.startsWith(FTL_PREFIX)) {
            return;
        }

        String outputPath = templateFileStream.getPath().substring(FTL_PREFIX.length() + 1);
        Map<String, Object> templateModel = new HashMap<>(codeGen.getTemplateModel());
        File outputFile = codeGen.getOutputFile(outputPath, codeGen.getTemplateModel());

        // Write it
        codeGen.writeOutputFile(
                outputFile,
                templatePath,
                templateFileStream.getStream(),
                templateModel);
    }
}
