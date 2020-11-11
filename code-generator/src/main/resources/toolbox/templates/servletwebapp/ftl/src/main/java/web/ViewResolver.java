package ${basePackage}.web;

import javax.servlet.http.HttpServletRequest;

/**
 * Given a simple name and resolve the file location of a view file.
 */
public class ViewResolver {
    private String viewNamePrefix = "/WEB-INF/jsp/";
    private String viewNameSuffix = ".jsp";

    public void setViewNamePrefix(String viewNamePrefix) {
        this.viewNamePrefix = viewNamePrefix;
    }

    public void setViewNameSuffix(String viewNameSuffix) {
        this.viewNameSuffix = viewNameSuffix;
    }

    public String resolveViewName(String name) {
        return viewNamePrefix + name + viewNameSuffix;
    }

    public String resolveViewName(HttpServletRequest req) {
        String contextPath = req.getContextPath();
        String name = req.getRequestURI().substring(contextPath.length() + 1);
        return viewNamePrefix + name + viewNameSuffix;
    }
}
