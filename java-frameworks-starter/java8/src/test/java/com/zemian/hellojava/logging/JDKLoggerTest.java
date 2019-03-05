package com.zemian.hellojava.logging;

import com.zemian.hellojava.AppException;
import org.junit.Test;

import java.util.logging.Level;
import java.util.logging.Logger;

public class JDKLoggerTest {
    public static Logger LOG = Logger.getLogger(JDKLoggerTest.class.getName());

    @Test
    public void test() {
        LOG.finest("TEST");
        LOG.fine("TEST");
        LOG.info("TEST");
        LOG.warning("TEST");
        LOG.log(Level.SEVERE, "TEST");

        LOG.log(Level.SEVERE, "TEST", new AppException("Just a test"));
    }
}
