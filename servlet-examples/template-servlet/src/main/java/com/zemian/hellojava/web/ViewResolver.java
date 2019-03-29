package com.zemian.hellojava.web;

/**
 * Given a simple name and resolve the file location of a view file.
 */
public class ViewResolver {
    private String viewNamePrefix = "/WEB-INF/jsp/";
    private String viewNameSuffix = ".jsp";

    public String resolveViewName(String name) {
        return viewNamePrefix + name + viewNameSuffix;
    }
}
