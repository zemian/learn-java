package com.zemian.hellojava.spring.componentscan.halftscan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {
    /*
    NOTE that Hello bean here does not need to be set with HelloB and yet @Autowire will take
    care of it! So this is an example of half creation of bean, and half injection. Kind of confusing
    but still works as expected.
     */
    @Bean
    public Hello hello() {
        return new Hello();
    }

    @Bean
    public HelloB helloB() {
        HelloB bean = new HelloB();
        bean.setName("test");
        return bean;
    }
}
