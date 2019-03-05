package com.zemian.hellojava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloService {
    private static Logger LOG = LoggerFactory.getLogger(HelloService.class);

    public String getGreetingMessage() {
        return "Hello World";
    }

    public void run() throws Exception {
        LOG.info(getGreetingMessage());
    }
}
