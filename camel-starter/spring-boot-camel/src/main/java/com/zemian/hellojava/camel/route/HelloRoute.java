package com.zemian.hellojava.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("camel-hello")
@Component
public class HelloRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("timer://camelTimer?fixedRate=true&period=3000").
                to("log:com.zemian.perfrunner.camel?level=INFO");
    }
}
