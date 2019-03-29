package com.zemian.perfrunner.camel.perf;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
public class LogTest {
    static CountDownLatch latch = new CountDownLatch(1000);

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("dataset:simpleDataSet").
                            to("log:camel?level=DEBUG").
                            process(exchange -> { latch.countDown(); });
                }
            };
        }

        @Bean
        public DataSet simpleDataSet() {
            SimpleDataSet b = new SimpleDataSet();
            b.setSize(1000);
            return b;
        }
    }

    @Test
    public void test() throws Exception {
        latch.await();
        Thread.sleep(500L);
    }
}

