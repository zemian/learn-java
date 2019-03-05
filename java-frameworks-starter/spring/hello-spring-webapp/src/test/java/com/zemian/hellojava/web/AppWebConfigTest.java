package com.zemian.hellojava.web;

import com.zemian.hellojava.SpringTestBase;
import com.zemian.hellojava.service.ServiceConfig;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.ServletContext;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * The config should load `hellojava/app.properties` and overriable by `application-test.properties`.
 */
@ContextConfiguration(classes = {ServiceConfig.class, AppWebConfig.class})
@WebAppConfiguration
public class AppWebConfigTest extends SpringTestBase {
    @Value("${app.env}")
    private String envName;

    @Autowired
    private ServletContext servletContext;

    @Test
    public void test() {
        assertThat(envName, is("TEST"));
        assertThat(servletContext, notNullValue());
    }
}
