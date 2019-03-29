package com.zemian.perfrunner.camel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * A main entry into the application and provide Spring Config settings.
 */
@SpringBootApplication
public class AppMain {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(AppMain.class, args);
    }
}
