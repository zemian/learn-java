package com.zemian.hellojava;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
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
    private DataSource dataSource;
    private Jdbc jdbc;
    private SettingDAO settingDAO;

    public SettingDAO getSettingDAO() {
        return settingDAO;
    }

    public Jdbc getJdbc() {
        return jdbc;
    }

    public Properties getProps() {
        return props;
    }

    public ViewResolver getViewResolver() {
        return viewResolver;
    }

    public DataSource getDataSource() {
        return dataSource;
    }


    //
    // Lifecycles
    //
    public void init() {
        props = AppUtils.loadAppProps();
        initDataSource();
        jdbc = new Jdbc(dataSource);
        settingDAO = new SettingDAO(jdbc);
    }

    private void initDataSource() {
//        PGSimpleDataSource ds = new PGSimpleDataSource();
//        ds.setUrl(props.getProperty("app.db.url"));
//        ds.setUser(props.getProperty("app.db.user"));
//        ds.setPassword(props.getProperty("app.db.password"));
//        dataSource = ds;

        // Lookup DataSource through JNDI
        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context)initCtx.lookup("java:comp/env");
            dataSource = (DataSource)envCtx.lookup("jdbc/appDataSource");
        } catch (Exception e) {
            throw new AppException("Failed to lookup DataSource 'appDataSource' from JNDI", e);
        }
    }

    public void destroy() {
    }
}
