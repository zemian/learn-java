package com.zemian.hellojava.freemarker;

import freemarker.template.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class FtlDefConfig {
    @Bean
    public Configuration defaultConfig() {
        Configuration bean = new Configuration(Configuration.VERSION_2_3_27);
        return bean;
    }

    @Bean
    public FtlService ftlService() {
        return new FtlService();
    }
}
