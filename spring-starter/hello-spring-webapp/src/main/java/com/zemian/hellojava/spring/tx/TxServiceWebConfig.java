package com.zemian.hellojava.spring.tx;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("demo-spring-tx")
@Configuration
public class TxServiceWebConfig {
    @Bean
    TxServiceTestController txServiceTestController() {
        return new TxServiceTestController();
    }
}
