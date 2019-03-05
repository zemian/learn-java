package com.zemian.hellojava.app;

import com.zemian.hellojava.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * A classic Hello world program in a Spring style.
 *
 * You may run this Hello as main program.
 */
@Component
public class Hello {

    public static void main(String[] args) {
        ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(Config.class);
        spring.getBean(Hello.class).run(args);
        spring.close();
    }

    @Configuration
    @Import(CommonConfig.class)
    public static class Config {
        @Bean
        public Hello hello() {
            return new Hello();
        }
    }

    private static final Logger LOG = LoggerFactory.getLogger(Hello.class);

    @Value("${app.env}")
    private String appEnv;

    @Value("${app.hello.message}")
    private String message;

    public void setGreetingMessage(String message) {
        this.message = message;
    }

    public String getAppEnv() {
        return appEnv;
    }

    public String getGreetingMessage() {
        return message;
    }

    public void run(String[] args) {
        LOG.debug("Args={}", Arrays.asList(args));
        run();
    }

    public void run() {
        LOG.info(getAppEnv() + ": " + getGreetingMessage());
    }
}
