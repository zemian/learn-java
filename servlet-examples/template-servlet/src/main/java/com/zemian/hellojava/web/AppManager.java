package com.zemian.hellojava.web;

import com.zemian.hellojava.AppUtils;
import org.slf4j.Logger;

import java.util.Date;
import java.util.Properties;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * A global singleton instance of a AppManager for the application. We use this to hold
 * and get access to the backend services.
 */
public class AppManager {
    private static final Logger LOG = getLogger(AppManager.class);

    //
    // Manage singleton instance
    //
    private AppManager() {}
    private static AppManager INSTANCE = new AppManager();
    public static AppManager getInstance() {
        return INSTANCE;
    }

    //
    // App Config
    //
    private Date startupTime = new Date();
    private Properties props = new Properties();
    private String contextPath;

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public String getEnv() {
        return props.getProperty("app.env");
    }

    public Date getStartupTime() {
        return startupTime;
    }

    //
    // App Services
    //
    private ViewResolver viewResolver = new ViewResolver();

    public Properties getProps() {
        return props;
    }

    public ViewResolver getViewResolver() {
        return viewResolver;
    }

    //
    // Lifecycles
    //
    public void init() {
        props = AppUtils.loadAppProps();
    }

    public void destroy() {
    }
}
