package com.zemian.hellojava.app;

import com.zemian.hellojava.AppException;
import com.zemian.hellojava.CommonConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class SpringServerTest {

    public static class Service1 {
        static AtomicBoolean activated = new AtomicBoolean(false);
        @PostConstruct
        public void init() {
            activated.set(true);
        }
    }

    public static class Service2 {
        static AtomicBoolean activated = new AtomicBoolean(false);
        @Autowired
        private Service1 service1;

        @PostConstruct
        public void init() {
            org.springframework.util.Assert.isTrue(service1 != null, "The service1 instance is not injected.");
            activated.set(true);
        }
    }

    @Configuration
    @Import(CommonConfig.class)
    public static class Config {
        @Bean
        public Service1 service1() {
            return new Service1();
        }
    }

    @Configuration
    @Import(CommonConfig.class)
    public static class Config2 {
        @Bean
        public Service2 service2() {
            return new Service2();
        }
    }

    @Before
    public void beforeTests() {
        Service1.activated.set(false);
        Service2.activated.set(false);
    }

    @Test
    public void startAndStop() throws Exception {
        SpringServer server = new SpringServer();
        new Thread(() -> server.start(Config.class)).start();
        Thread.sleep(3000L);

        assertThat(Service1.activated.get(), is(true));
        assertThat(Service2.activated.get(), is(false));
        assertThat(server.isStarted(), is(true));
        assertThat(server.getSpring(), notNullValue());
        server.stop();
        assertThat(server.isStarted(), is(false));
        assertThat(server.getSpring(), nullValue());
    }

    @Test
    public void multipleConfigs() throws Exception {
        SpringServer server = new SpringServer();
        new Thread(() -> server.start(Config.class, Config2.class)).start();
        Thread.sleep(3000L);

        assertThat(Service1.activated.get(), is(true));
        assertThat(Service2.activated.get(), is(true));
        assertThat(server.isStarted(), is(true));
        assertThat(server.getSpring(), notNullValue());
        server.stop();
        assertThat(server.isStarted(), is(false));
        assertThat(server.getSpring(), nullValue());
    }

    @Test
    public void main() throws Exception {
        String[] args = new String[] {
                "com.zemian.hellojava.app.SpringServerTest$Config",
                "com.zemian.hellojava.app.SpringServerTest$Config2"
        };

        new Thread(() -> SpringServer.main(args)).start();
        Thread.sleep(3000L);

        assertThat(Service1.activated.get(), is(true));
        assertThat(Service2.activated.get(), is(true));
    }

    @Test(expected = AppException.class)
    public void emptyArgs() throws Exception {
        SpringServer server = new SpringServer();
        // You must give at least one argument, else it should throw exception.
        server.start(new Class<?>[]{});
    }

    @Test
    public void stopWithoutStart() throws Exception {
        SpringServer server = new SpringServer();
        // This should do nothing since server is not started yet.
        assertThat(server.isStarted(), is(false));
        server.stop();
        assertThat(server.isStarted(), is(false));
        assertThat(server.getSpring(), nullValue());
    }

    @Test
    public void startTwice() throws Exception {
        SpringServer server = new SpringServer();
        new Thread(() -> server.start(Config.class)).start();
        Thread.sleep(3000L);

        assertThat(Service1.activated.get(), is(true));
        assertThat(Service2.activated.get(), is(false));
        assertThat(server.isStarted(), is(true));
        assertThat(server.getSpring(), notNullValue());

        try {
            server.start(Config.class);
            Assert.fail("Server should not start twice.");
        } catch (AppException e) {

        }

        server.stop();
        assertThat(server.isStarted(), is(false));
        assertThat(server.getSpring(), nullValue());
    }
}
