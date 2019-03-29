package com.zemian.hellojava.spring.envconfig;

import com.zemian.hellojava.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;

/*
We can optionally load a props resource ONLY if it exists!

To test this config, run it once as it, then secondly rename "app-test.properties.bak" into "app-test.properties".
 */
public class OptinalPropsHelloApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config.class);

        ConfigurableEnvironment env = spring.getEnvironment();
        System.out.println("Env=" + env);

        DataGenerator bean = spring.getBean(DataGenerator.class);
        System.out.println("DataGenerator=" + bean);
    }

    @Configuration
    @PropertySource("classpath:/hellojava/app.properties")
    @PropertySource(value = "classpath:/hellojava/app-test.properties", ignoreResourceNotFound = true)
    public static class Config {
        @Autowired
        private Environment env;

        @Bean
        public DataGenerator dataGenerator() {
            DataGenerator bean = new DataGenerator();
            bean.setName(env.getProperty("app.env"));
            return bean;
        }
    }
}
