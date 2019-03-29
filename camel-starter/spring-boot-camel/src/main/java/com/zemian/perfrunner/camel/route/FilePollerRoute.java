package com.zemian.perfrunner.camel.route;

import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("camel-filepoller")
@Component
public class FilePollerRoute extends RouteBuilder {
    private static final Logger LOG = LoggerFactory.getLogger(FilePollerRoute.class);

    @Override
    public void configure() throws Exception {
        String filePollerDir = "target/file-poller";
        from("file://" + filePollerDir).
                to("log:com.zemian.perfrunner.camel?level=INFO&groupSize=100");
    }
}
