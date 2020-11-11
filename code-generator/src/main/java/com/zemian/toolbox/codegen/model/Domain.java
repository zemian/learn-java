package com.zemian.toolbox.codegen.model;

import com.zemian.toolbox.support.JavaUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Domain {
    private String packageName;
    private String className;
    private String classVar;
    private List<Field> fields = new ArrayList<>();

    public Domain(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
        this.classVar = JavaUtils.lowCase(className);
    }

    public List<Field> getFields() {
        return fields;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public String getClassVar() {
        return classVar;
    }

    public String getClassNameUrl() {
        String uName = JavaUtils.camelToUnderscore(classVar);
        return uName.replaceAll("_", "-");
    }

    // == Special Domain Field Getters

    public List<Field> getNonKeyFields() {
        return getFields().stream().filter(f -> !f.getDbField().isKey()).collect(Collectors.toList());
    }

    public List<Field> getKeyFields() {
        return getFields().stream().filter(f -> f.getDbField().isKey()).collect(Collectors.toList());
    }

    // == Special DdField Getters

    public String getNonKeyDbFieldNames() {
        return getNonKeyFields().stream().map(f -> f.getDbField().getColumnName()).collect(Collectors.joining(", "));
    }

    public String getNonKeyQMarks() {
        return getNonKeyFields().stream().map(f -> "?").collect(Collectors.joining(", "));
    }

    public String getDbFieldNames() {
        return getFields().stream().map(f -> f.getDbField().getColumnName()).collect(Collectors.joining(", "));
    }

    public String getDbFieldQMarks() {
        return getFields().stream().map(f -> "?").collect(Collectors.joining(", "));
    }
}
