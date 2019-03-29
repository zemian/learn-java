package com.zemian.hellojava.spring.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HelloService implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(HelloService.class);

    @Value("${app.env}")
    private String appEnv;

    @Value("${app.hello.message}")
    private String message;

    public String getAppEnv() {
        return appEnv;
    }

    public String getGreetingMessage() {
        return message;
    }

    public void run() {
        LOG.info(getAppEnv() + ": " + getGreetingMessage());
    }
}
