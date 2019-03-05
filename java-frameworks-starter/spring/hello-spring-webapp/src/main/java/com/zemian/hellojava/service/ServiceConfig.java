package com.zemian.hellojava.service;

import com.zemian.hellojava.spring.tx.TxServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/*
This class override the one defined in hello-spring-common with same package name.
We want this class so we can experiment other service config demos.
 */
@Configuration
@Import({
        CommonServiceConfig.class,
        TxServiceConfig.class
})
public class ServiceConfig {
    @Profile("demo-dbprops-env")
    @Bean
    DbPropsEnvironment dbPropsEnvironment() {
        return new DbPropsEnvironment();
    }
}
