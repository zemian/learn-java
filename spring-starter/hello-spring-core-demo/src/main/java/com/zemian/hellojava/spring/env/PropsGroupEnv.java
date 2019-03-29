package com.zemian.hellojava.spring.env;

import com.zemian.hellojava.DataGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/*
NOTES:

The last @PropertySource always override the ones above it!
However when pringing the Environment.getPropertySources() the order of filenames printed is reversed.

 */
public class PropsGroupEnv {

    @Configuration
    @PropertySource("classpath:/hellojava/app.properties")
    @PropertySource("classpath:/com/zemian/hellojava/spring/env/mygroup.properties")
    public static class Config {
        @Bean
        public DataGenerator dataGenerator() {
            DataGenerator bean = new DataGenerator("test");
            return bean;
        }
    }

    @Configuration
    @PropertySource("classpath:/com/zemian/hellojava/spring/env/mygroup.properties")
    @PropertySource("classpath:/hellojava/app.properties")
    public static class Config2 {
        @Bean
        public DataGenerator dataGenerator() {
            DataGenerator bean = new DataGenerator("test");
            return bean;
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("== main1");
        main1(args);
        System.out.println("== main2");
        main2(args);
        System.out.println("== main4");
        main4(args);
    }

    public static void main1(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config.class);
        ConfigurableEnvironment env = spring.getEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        for (org.springframework.core.env.PropertySource<?> source : sources) {
            System.out.println("PropertySource=" + source);
        }

        String appEnv = env.getProperty("app.env");
        System.out.println("appEnv=" + appEnv);
    }

    public static void main2(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config2.class);
        ConfigurableEnvironment env = spring.getEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        for (org.springframework.core.env.PropertySource<?> source : sources) {
            System.out.println("PropertySource=" + source);
        }

        String appEnv = env.getProperty("app.env");
        System.out.println("appEnv=" + appEnv);
    }

    public static void main4(String[] args) {
        // Get a list of all property names from Spring env

        List<String> propNames = new ArrayList<>();
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(Config2.class);
        ConfigurableEnvironment env = spring.getEnvironment();
        MutablePropertySources sources = env.getPropertySources();
        for (org.springframework.core.env.PropertySource<?> source : sources) {
            if (source instanceof EnumerablePropertySource) {
                EnumerablePropertySource<Object> enuSource = (EnumerablePropertySource<Object>)source;
                propNames.addAll(Arrays.asList(enuSource.getPropertyNames()));
            }
        }
        for (String name : propNames) {
            System.out.println("Property name=" + name);
        }
    }
}
