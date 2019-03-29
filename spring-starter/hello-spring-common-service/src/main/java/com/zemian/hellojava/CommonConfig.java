package com.zemian.hellojava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Main Spring Java Config
 *
 * @Author Zemian Deng 2017
 */
@Configuration
@PropertySource("classpath:/hellojava/app.properties")
@PropertySource(value = "classpath:/hellojava/${hellojava.env}/app.properties", ignoreResourceNotFound = true)
public class CommonConfig {
    /* This special bean needs to be static to resolve `${variable}` in @Value injection. */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer bean = new PropertySourcesPlaceholderConfigurer();
        return bean;
    }
}
