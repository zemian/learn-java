package com.zemian.hellojava.spring.componentscan;


import com.zemian.hellojava.SpringTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ContextConfiguration(classes = App1AutoScanConfig.class)
public class App1Test extends SpringTestBase {
    @Autowired
    private MyService myService;

    @Test
    public void test() {
        assertThat(myService.getName(), is ("MyService1"));
    }
}
