package com.zemian.hellojava.web;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AppManagerTest {
    static AppManager app = AppManager.getInstance();

    @BeforeClass
    public static void beforeClass() {
        // Ensure to init app manager for first time!
        app.init();
    }

    @AfterClass
    public static void afterClass() {
        app.destroy();
    }

    @Test
    public void appProps() {
        Properties props = app.getProps();
        assertThat(props.getProperty("app.env"), is("TEST"));

    }
}
