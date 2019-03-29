package com.zemian.hellojava.spring.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Main Spring Java Config
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class AppMainConfig {
    /* This special bean needs to be static to resolve `${variable}` in @Value injection. */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        return bean;
    }
}
