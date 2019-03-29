package com.zemian.hellojava;

import org.junit.Test;

import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AppUtilsTest {
    @Test
    public void testProps() {
        Properties p = AppUtils.loadAppProps();
        assertThat(p.getProperty("app.env"), is("TEST"));
    }
}
