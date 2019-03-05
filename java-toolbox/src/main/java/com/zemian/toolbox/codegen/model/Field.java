package com.zemian.toolbox.codegen.model;

import com.zemian.toolbox.support.JavaUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Field {
    private String type;
    private String name;
    private String getterMethodName;
    private String setterMethodName;
    private DbField dbField;
    private List<String> enumValues;

    public Field(String type, String name, String getterMethodName, String setterMethodName) {
        this.type = type;
        this.name = name;
        this.getterMethodName = getterMethodName;
        this.setterMethodName = setterMethodName;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNameLabel() {
        String underScore = JavaUtils.camelToUnderscore(name);
        String[] parts = underScore.split("_");
        return Arrays.stream(parts).map(e -> JavaUtils.upCase(e)).collect(Collectors.joining(" "));
    }

    public String getNameUrl() {
        String uName = JavaUtils.camelToUnderscore(name);
        return uName.replaceAll("_", "-");
    }

    public String getGetterMethodName() {
        return getterMethodName;
    }

    public String getSetterMethodName() {
        return setterMethodName;
    }

    public DbField getDbField() {
        return dbField;
    }

    public void setDbField(DbField dbField) {
        this.dbField = dbField;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    public void setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEnumType() {
        return JavaUtils.upCase(name);
    }
}
