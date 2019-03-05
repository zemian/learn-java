package com.zemian.perfrunner.camel.perf.tcp;

import org.apache.camel.CamelContext;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ComponentScan
public class Netty4SendReceiveTest {

    @Autowired
    private CamelContext camel;

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("netty4:tcp://localhost:5150").
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
        String endpoint = "netty4:tcp://localhost:5150";
        ProducerTemplate template = camel.createProducerTemplate();
        for (int i = 0; i < 50_000; i++) {
            template.sendBody(endpoint, "<hello/>");
        }
        Thread.sleep(30_000L);
    }
}
