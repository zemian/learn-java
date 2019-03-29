package com.zemian.hellojava.spring.componentscan.halftscan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloMain {
    private static Logger LOG = LoggerFactory.getLogger(HelloMain.class);
    public static void main(String[] args) {
        ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(HelloConfig.class);
        Hello hello = spring.getBean(Hello.class);
        LOG.info("HelloB name={}", hello.getHelloB().getName());
        spring.close();
    }
}
