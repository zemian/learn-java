package com.zemian.hellojava.service;

import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * You need to run and setup hello-spring-webapp.sql before running this test.
 */
@ContextConfiguration(classes = DbPropsEnvironmentTest.Config.class)
public class DbPropsEnvironmentTest extends SpringTestBase {
    @Autowired
    private Environment env;

    @Configuration
    public static class Config {
        @Bean
        public DbPropsEnvironment dbPropsEnvironment() {
            DbPropsEnvironment bean = new DbPropsEnvironment();
            return bean;
        }
    }

    @Test
    public void dbAppConfigProps() {
        assertThat(env.getProperty("app.web.htmlTitle"), is("Spring Demo"));
    }
}