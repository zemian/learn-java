package com.zemian.perfrunner.camel.experiment;

import com.zemian.perfrunner.AppException;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A standalone Camel main startup class. It will take an optional spring beanName as argument
 * and use it to lookup Route bean if given. Else default to singleton Route and startup
 * spring context as server and wait for CTRL+C to terminate.
 */
@SpringBootApplication
public class CamelPerfApp {
    private static final Logger LOG = LoggerFactory.getLogger(CamelPerfApp.class);
    protected AtomicBoolean running = new AtomicBoolean(true);
    protected Object waitLock = new Object();

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext spring = SpringApplication.run(CamelPerfApp.class, args);

        RouteBuilder routeBuilder;
        if (args.length > 0 && spring.containsBean(args[0])) {
            routeBuilder = spring.getBean(args[0], RouteBuilder.class);
        } else {
            routeBuilder = spring.getBean(RouteBuilder.class);
        }
        CamelPerfApp service = spring.getBean(CamelPerfApp.class);
        service.run(routeBuilder);
    }

    public void run(RouteBuilder routeBuilder) throws Exception {
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(routeBuilder);

        // Register shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                context.stop();
                running.set(false);
                synchronized (waitLock) {
                    waitLock.notify();
                }
            } catch (Exception e) {
                throw new AppException("Failed to stop Camel.", e);
            }
        }));

        // Start the Camel
        context.start();
        LOG.info("CamelPefApp started {}", context);

        while (running.get()) {
            synchronized (waitLock) {
                waitLock.wait(Long.MAX_VALUE);
            }
        }
    }
}
