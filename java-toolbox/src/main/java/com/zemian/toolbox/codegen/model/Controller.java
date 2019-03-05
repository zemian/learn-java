package com.zemian.toolbox.codegen.model;

import com.zemian.toolbox.support.JavaUtils;

public class Controller {
    private String packageName;
    private String className;
    private String classVar;
    private Service service;
    private String webUiUrlMapping;

    public Controller(String packageName, Service service, String webUiUrlMapping) {
        this.packageName = packageName;
        this.service = service;
        this.webUiUrlMapping = webUiUrlMapping;

        this.className = service.getDao().getDomain().getClassName() + "Controller";
        this.classVar = JavaUtils.lowCase(className);;
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

    public Service getService() {
        return service;
    }

    public String getWebUiUrlMapping() {
        return webUiUrlMapping;
    }
}
