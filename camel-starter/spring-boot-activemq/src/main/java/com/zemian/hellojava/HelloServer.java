package com.zemian.hellojava;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This is a spring server that listen and process JMS message.
 * We assume a JMS Broker (ActiveMQ) is already running separately.
 */
@SpringBootApplication
public class HelloServer implements Runnable {
    private static final Logger LOG = getLogger(HelloServer.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext spring = SpringApplication.run(HelloServer.class, args);
        HelloServer bean = spring.getBean(HelloServer.class);
        bean.run();
        spring.close();
    }

    @Autowired
    private ConfigurableApplicationContext spring;

    @Override
    public void run() {
        LOG.debug("Hello Server is running.");
        while (spring.isRunning()) {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                LOG.debug("Main thread sleep interrupted.", e);
            }
        }
    }
}
