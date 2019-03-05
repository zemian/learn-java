package com.zemian.hellojava.camel;

import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
public class DirectTest {
    /* "direct" - Synchronous call to another endpoint from same CamelContext. */
    @Produce(uri = "direct:start")
    protected ProducerTemplate template;

    @Configuration
    public static class ContextConfig extends SingleRouteCamelConfiguration {
        @Bean
        public RouteBuilder route() {
            return new RouteBuilder() {
                public void configure() {
                    from("direct:start").
                            to("log:camel");
                }
            };
        }
    }

    @Test
    public void test() throws Exception {
        String expectedBody = "<hello/>";
        template.sendBody(expectedBody);
    }
}
