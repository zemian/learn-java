package com.zemian.hellojava.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * A classic Hello world program in a Spring style.
 *
 * You may run this Hello as main program.
 */
@SpringBootApplication
public class Hello {

    public static void main(String[] args) {
        ConfigurableApplicationContext spring = SpringApplication.run(Hello.class, args);
        spring.getBean(Hello.class).run(args);
        spring.close();
    }

    private static final Logger LOG = LoggerFactory.getLogger(Hello.class);

    @Value("${app.env}")
    private String appEnv;

    @Value("${app.hello.message}")
    private String message;

    public void setGreetingMessage(String message) {
        this.message = message;
    }

    public String getAppEnv() {
        return appEnv;
    }

    public String getGreetingMessage() {
        return message;
    }

    public void run(String[] args) {
        LOG.debug("Args={}", Arrays.asList(args));
        run();
    }

    public void run() {
        LOG.info(getAppEnv() + ": " + getGreetingMessage());
    }
}
