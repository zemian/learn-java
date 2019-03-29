package com.zemian.hellojava.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Configuration
@Import(RunnerConfig.class)
@Component
public class HelloTimer extends BaseRunner {
    @Override
    protected void initCamel() throws Exception {
        camel.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("timer://camelTimer?fixedRate=true&period=3000").
                        to("log:com.zemian.hellojava.camel.HelloTimer?level=INFO");
            }
        });
    }
}
