package com.zemian.toolbox.codegen;

import java.util.HashMap;
import java.util.Map;

public class Config {
    /**
     * Add global template models to all templates.
     */
    private Map<String, Object> templateModel;

    /**
     * Map a templatePath within a templateSet for outputFile that may contains templateModel variable
     * for expansion.
     */
    private Map<String, String> templatePathToOutputFiles;

    /**
     * Map from DbField.dataType to a Domain field type as full className.
     */
    private Map<String, String> dbDataTypeToJavaTypes;

    public Map<String, String> getDbDataTypeToJavaTypes() {
        return dbDataTypeToJavaTypes;
    }

    public void setDbDataTypeToJavaTypes(Map<String, String> dbDataTypeToJavaTypes) {
        this.dbDataTypeToJavaTypes = dbDataTypeToJavaTypes;
    }

    public Map<String, String> getTemplatePathToOutputFiles() {
        return templatePathToOutputFiles;
    }

    public void setTemplatePathToOutputFiles(Map<String, String> templatePathToOutputFiles) {
        this.templatePathToOutputFiles = templatePathToOutputFiles;
    }

    public Map<String, Object> getTemplateModel() {
        return templateModel;
    }

    public void setTemplateModel(Map<String, Object> templateModel) {
        this.templateModel = templateModel;
    }
}
