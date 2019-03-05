package com.zemian.perfrunner.camel.perf.jms;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * We assume the Queue1 already has message populdated before this test is called.
 */
@RunWith(CamelSpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ComponentScan
public class Jms2ReaderTest {

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("jms:queue:Queue1").
                            to("dataset:simpleDataSet");
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
        Thread.sleep(30_000L);
    }
}

