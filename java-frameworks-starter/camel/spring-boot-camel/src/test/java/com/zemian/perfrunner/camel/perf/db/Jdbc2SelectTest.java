package com.zemian.perfrunner.camel.perf.db;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.SimpleDataSet;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * Ensure SpringBoot is loaded for the test in order to discover and autoconfigure the DataSource.
 */
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ComponentScan
public class Jdbc2SelectTest {

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {

        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("dataset:simpleDataSet").
                            to("jdbc:dataSource");
                }
            };
        }

        @Bean
        public DataSet simpleDataSet() {
            SimpleDataSet b = new SimpleDataSet();
            b.setDefaultBody("SELECT * FROM test_perf_camel");
            b.setSize(1000);
            return b;
        }
    }

    @Test
    public void test() throws Exception {
        Thread.sleep(30_000L);
    }
}

