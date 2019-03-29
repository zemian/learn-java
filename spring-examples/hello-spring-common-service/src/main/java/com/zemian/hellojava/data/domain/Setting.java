package com.zemian.hellojava.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Arrays;

/**
 * A domain POJO mapped to DB table settings.
 *
 * This class is generated by Zemian's CodeGen Toolbox on Jan 1, 2018.
 */
public class Setting {
    public static enum Type {
        STRING, BOOLEAN, INTEGER, DOUBLE, LIST;
    }

    private Integer settingId; // key
    private String category;
    private String name;
    private String value;
    private Type type;
    private String description;

    public Integer getSettingId() {
        return this.settingId;
    }
    public void setSettingId(Integer settingId) {
        this.settingId = settingId;
    }
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder fields = new StringBuilder();
        fields.append("settingId=" + getSettingId());
        return "Setting{" + fields.toString() + "}";
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T> T getValueByType() {
        if (type == Type.BOOLEAN) {
            return (T)Boolean.valueOf(value);
        } else if (type == Type.INTEGER) {
            return (T)Integer.valueOf(value);
        } else if (type == Type.DOUBLE) {
            return (T)Double.valueOf(value);
        } else if (type == Type.LIST) {
            return (T) Arrays.asList(value.split(","));
        } else {
            return (T)value;
        }
    }

    public Setting() {
    }

    // == Customize

    public Setting(String category, String name, String value) {
        this.category = category;
        this.name = name;
        this.value = value;
    }
}
