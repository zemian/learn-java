package com.zemian.hellojava;

import org.slf4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static org.slf4j.LoggerFactory.getLogger;

@Component
public class HelloListener {
    private static final Logger LOG = getLogger(HelloListener.class);
    public static final String TEST_QUEUE = "TestQ1";

    @JmsListener(destination = TEST_QUEUE)
    public void receiveMessage(String msg) {
        LOG.info("Received msg: {}", msg);
    }
}
