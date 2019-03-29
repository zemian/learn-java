package com.zemian.perfrunner.camel.perf.dataformat;

import com.zemian.perfrunner.camel.data.Person;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.dataset.DataSet;
import org.apache.camel.component.dataset.ListDataSet;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@RunWith(CamelSpringRunner.class)
@SpringBootTest
@EnableAutoConfiguration
@ComponentScan
public class JsonMarshallTest {
    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("dataset:simpleDataSet").
                            marshal().json(JsonLibrary.Jackson);
                }
            };
        }

        @Bean
        public DataSet simpleDataSet() {
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                list.add(new Person("Tester" + i, "Camel"));
            }
            ListDataSet b = new ListDataSet(list);
            return b;
        }
    }

    @Test
    public void test() throws Exception {
        Thread.sleep(30_000L);
    }
}

