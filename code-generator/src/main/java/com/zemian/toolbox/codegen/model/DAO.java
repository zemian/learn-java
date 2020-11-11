package com.zemian.toolbox.codegen.model;

import com.zemian.toolbox.support.JavaUtils;

public class DAO {
    private String packageName;
    private String className;
    private String classVar;
    private Domain domain;

    public DAO(String packageName, Domain domain) {
        this.packageName = packageName;
        this.domain = domain;
        this.className = domain.getClassName() + "DAO";
        this.classVar = JavaUtils.lowCase(className);;
    }

    public String getClassVar() {
        return classVar;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public Domain getDomain() {
        return domain;
    }
}
