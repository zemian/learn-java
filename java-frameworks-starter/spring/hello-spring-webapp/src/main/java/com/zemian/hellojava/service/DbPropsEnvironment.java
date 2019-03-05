package com.zemian.hellojava.service;

import com.zemian.hellojava.data.domain.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This service will load all records from settings table with category match to
 * APP_WEB_CATEGORY_PREFIX + [app.env], where [app.env] must be defined in 'hellojava/app.properties' file first.
 * These DB settings will override the one found in 'hellojava/app.properties'.
 *
 * NOTE: This is a quickly implmentaiton of adding DB props souce into the Spring Env. If you have code that
 * read the Environment before this service is initialized, then you will NOT able to see the DB props! Care
 * must be taken to ensure the order of this service is loaded first. One way to do this is to make this
 * as "dependended" field in the service where  you need to read Environment.
 */
@Service
public class DbPropsEnvironment {
    private static Logger LOG = LoggerFactory.getLogger(DbPropsEnvironment.class);

    public static final String APP_WEB_CATEGORY_PREFIX = "APP_WEB_";
    public static final String APP_ENV_KEY = "app.env";

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Autowired
    private SettingService settingService;

    @PostConstruct
    public void init() {
        String category = APP_WEB_CATEGORY_PREFIX + configurableEnvironment.getProperty(APP_ENV_KEY);
        LOG.info("Loading {} from settings db table.", category);
        Map<String, Object> dbSettings = settingService.findByCategory(category).stream().collect(
                Collectors.toMap(Setting::getName, s -> s.getValue()));

        LOG.debug("Adding {} settings into Spring env", dbSettings.size());
        MapPropertySource dbSettingsSource = new MapPropertySource("dbSettings", dbSettings);
        configurableEnvironment.getPropertySources().addFirst(dbSettingsSource);
    }
}
