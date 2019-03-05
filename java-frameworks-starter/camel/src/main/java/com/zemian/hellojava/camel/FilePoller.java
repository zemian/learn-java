package com.zemian.hellojava.camel;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

@Configuration
@Import(RunnerConfig.class)
@Component
public class FilePoller extends BaseRunner {
    @Override
    protected void initCamel() throws Exception {
        camel.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                String filePollerDir = "target/file-poller";
                from("file://" + filePollerDir).
                        process(exchange -> {
                            log.info("Received file: " + exchange.getIn().getBody());
                        });
            }
        });
    }
}
