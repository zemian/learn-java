package com.zemian.hellojava;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A main entry into the application.
 */
public class AppStartup {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppSpringConfig.class, args);
	}
}
