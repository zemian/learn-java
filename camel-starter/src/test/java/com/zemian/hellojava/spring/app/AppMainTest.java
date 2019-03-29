package com.zemian.hellojava.spring.app;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class AppMainTest {
    @Test
    public void emptyArgs() throws Exception {
        AppMain main = new AppMain();
        new Thread(() -> main.run(new String[]{})).start();
        Thread.sleep(3000L);
        assertThat(main.isRunning(), is(true));
        main.stopMain();
        assertThat(main.isRunning(), is(false));
    }

    @Test
    public void javaConfigArg() throws Exception {
        AppMain main = new AppMain();
        new Thread(() -> main.run(new String[]{ "com.zemian.hellojava.spring.hello.HelloConfig" })).start();
        Thread.sleep(3000L);
        assertThat(main.isRunning(), is(true));
        main.stopMain();
        assertThat(main.isRunning(), is(false));
    }

    @Test
    public void runnableBeanArg() throws Exception {
        AppMain main = new AppMain();
        // It should not be a blocking call.
        main.run(new String[]{ "com.zemian.hellojava.spring.hello.HelloConfig", "helloService" });
        assertThat(main.isRunning(), is(false));
    }
}
