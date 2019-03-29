package com.zemian.perfrunner.camel;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class HelloTest {
    @Test
    public void test() {
        assertThat(1 + 1, is(2));
    }
}
