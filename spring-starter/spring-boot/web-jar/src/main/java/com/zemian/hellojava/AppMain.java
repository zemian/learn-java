package com.zemian.hellojava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * A main entry into the application and Spring Java Config settings.
 */
@SpringBootApplication
public class AppMain {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(AppMain.class, args);
	}

	/**
	 * Customize Spring MVC specific. Many of these can be set in SpringBoot's application.properties,
	 * but some we need to customize here.
	 */
	@Configuration
	public static class AppWebMvcConfig extends WebMvcConfigurerAdapter {
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addViewController("/about").setViewName("about");
		}
	}
}
