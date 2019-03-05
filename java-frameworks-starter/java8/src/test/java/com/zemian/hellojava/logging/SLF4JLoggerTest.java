package com.zemian.hellojava.logging;

import com.zemian.hellojava.AppException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JLoggerTest {
    private static final Logger LOG = LoggerFactory.getLogger(SLF4JLoggerTest.class);

    @Test
    public void test() {
        LOG.trace("TEST");
        LOG.debug("TEST");
        LOG.info("TEST");
        LOG.warn("TEST");
        LOG.error("TEST");

        LOG.error("TEST", new AppException("Just a test"));

        LOG.info("TEST with param1={}, param2={}", 1234, "foo");
    }
}
