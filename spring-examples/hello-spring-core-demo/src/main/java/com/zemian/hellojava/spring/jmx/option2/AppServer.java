package com.zemian.hellojava.spring.jmx.option2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class AppServer {
    private static final Logger LOG = LoggerFactory.getLogger(AppServer.class);

    private Object waitLock = new Object();

    @Autowired
    private ConfigurableApplicationContext spring;

    public Date getStartupDate() {
        return new Date(spring.getStartupDate());
    }

    public String[] getBeanDefinitionNames() {
        return spring.getBeanDefinitionNames();
    }

    public void run(String[] args) throws Exception {
        spring.registerShutdownHook();
        LOG.info("Server is running.");
        while (spring.isRunning()) {
            synchronized (waitLock) {
                waitLock.wait(1300L);
            }
        }
        LOG.info("Server is about to exit.");
    }
}
