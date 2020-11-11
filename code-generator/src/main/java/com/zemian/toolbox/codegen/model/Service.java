package com.zemian.toolbox.codegen.model;

import com.zemian.toolbox.support.JavaUtils;

public class Service {
    private String packageName;
    private String className;
    private String classVar;
    private DAO dao;

    public Service(String packageName, DAO dao) {
        this.packageName = packageName;
        this.dao = dao;

        this.className = dao.getDomain().getClassName() + "Service";
        this.classVar = JavaUtils.lowCase(className);
    }

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public DAO getDao() {
        return dao;
    }

    public String getClassVar() {
        return classVar;
    }
}
