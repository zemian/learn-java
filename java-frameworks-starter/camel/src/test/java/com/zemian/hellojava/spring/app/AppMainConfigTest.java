package com.zemian.hellojava.spring.app;

import com.zemian.hellojava.BaseSpringTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test default test class and its Spring configuration are working.
 * This should load SpringTestConfig and AppMainConfig.
 *
 * The config should load `application.properties` and overriable by `application-test.properties`.
 */
public class AppMainConfigTest extends BaseSpringTest {
    @Value("${app.env}")
    private String envName;

    @Value("${app.hello.message}")
    private String helloMessage;

    @Test
    public void test() {
        assertThat(envName, is("TEST"));
        assertThat(helloMessage, is("Hello World"));
    }
}