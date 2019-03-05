package com.zemian.hellojava.camel;

import com.zemian.hellojava.AppException;
import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseRunner implements Runnable {
    private Logger log = LoggerFactory.getLogger(getClass());

    protected AtomicBoolean running = new AtomicBoolean(true);
    protected Object waitLock = new Object();
    protected DefaultCamelContext camel;

    public CamelContext getCamel() {
        return camel;
    }

    @Override
    public void run() {
        try {
            camel = new DefaultCamelContext();
            initCamel();
            log.info("Starting Camel.");
            camel.start();
            log.info("Camel started.");

            while (running.get()) {
                synchronized (waitLock) {
                    waitLock.wait();
                }
            }
        } catch (Exception e) {
            throw new AppException("Failed to start Camel.", e);
        }
    }

    protected abstract void initCamel() throws Exception;

    @PreDestroy
    public void destroy() throws Exception {
        log.info("Stopping Camel.");
        if (camel != null) {
            camel.stop();
        }
        running.set(false);
        synchronized (waitLock) {
            waitLock.notify();
        }
        log.info("Camel stopped.");
    }
}
