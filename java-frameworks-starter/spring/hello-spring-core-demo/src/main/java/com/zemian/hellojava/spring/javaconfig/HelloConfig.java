package com.zemian.hellojava.spring.javaconfig;

import com.zemian.hellojava.Hello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {
    @Bean
    public Hello helloService() {
        Hello bean = new Hello();
        bean.setName("Hello World");
        return new Hello();
    }
}
