package com.zemian.hellojava;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test default test class and its Spring configuration are working with CommonConfig.
 */
public class CommonConfigTest extends SpringTestBase {
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