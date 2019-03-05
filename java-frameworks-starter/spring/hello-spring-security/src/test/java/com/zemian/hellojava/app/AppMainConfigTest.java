package com.zemian.hellojava.app;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test default test class and its Spring configuration are working.
 * This should load SpringTestConfig and AppMainConfig.
 *
 * The config should load `hellojava/app.properties` and overriable by `application-test.properties`.
 */
public class AppMainConfigTest extends SpringTestBase {
    @Value("${app.env}")
    private String envName;

    @Test
    public void test() {
        assertThat(envName, is("TEST"));
    }
}
