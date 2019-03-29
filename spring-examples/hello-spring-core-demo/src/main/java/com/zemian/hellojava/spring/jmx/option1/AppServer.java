package com.zemian.hellojava.spring.jmx.option1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@ManagedResource
public class AppServer {
    private static final Logger LOG = LoggerFactory.getLogger(AppServer.class);

    private Object waitLock = new Object();

    @Autowired
    private ConfigurableApplicationContext spring;

    @ManagedAttribute
    public Date getStartupDate() {
        return new Date(spring.getStartupDate());
    }

    @ManagedAttribute
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
