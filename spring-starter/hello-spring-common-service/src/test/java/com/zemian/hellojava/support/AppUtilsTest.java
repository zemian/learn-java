package com.zemian.hellojava.support;

import org.junit.Test;
import org.springframework.core.io.Resource;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

public class AppUtilsTest {

    @Test
    public void getReleaseProps() {
        Properties relProps = AppUtils.getReleaseProps();
        assertThat(relProps.get("commit-id"), is("HEAD"));
        assertThat(relProps.get("version"), is("SNAPSHOT"));
        assertThat(relProps.get("build-date"), notNullValue());
    }

    @Test
    public void resourceNotFound() {
        Resource envRes = AppUtils.getEnvResource("foo.data");
        assertThat(envRes, nullValue());
    }

    @Test
    public void resource() throws Exception {
        String origEnv = System.getProperty(AppUtils.APP_ENV_KEY);
        try {
            // Test case without env var set
            if (origEnv != null) {
                System.getProperties().remove(AppUtils.APP_ENV_KEY);
            }
            Resource envRes = AppUtils.getEnvResource("logback.xml");
            assertThat(envRes.getURL().toString(), endsWith("hellojava/logback.xml"));

            // Test case WITH env var set
            System.setProperty(AppUtils.APP_ENV_KEY, "qa");
            envRes = AppUtils.getEnvResource("logback.xml");
            assertThat(envRes.getURL().toString(), endsWith("hellojava/qa/logback.xml"));
        } finally {
            if (origEnv != null) {
                System.setProperty(AppUtils.APP_ENV_KEY, origEnv);
            } else {
                System.getProperties().remove(AppUtils.APP_ENV_KEY);
            }
        }
    }

    @Test
    public void getResourceProperties() {
        Properties appProps = AppUtils.getResourceProperties("app.properties");
        assertThat(appProps.get("app.env"), is("DEV"));
    }
}