package com.zemian.hellojava.spring.jmx.option1;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * A main entry into Spring application and its Java Config settings.
 */
@Configuration
@ComponentScan
@PropertySource("classpath:/hellojava/app.properties")
@EnableMBeanExport
public class AppMain1 {
	public static void main(String[] args) throws Exception {
		ConfigurableApplicationContext spring = new AnnotationConfigApplicationContext(AppMain1.class);
		AppServer server = spring.getBean(AppServer.class);
		server.run(args);
	}

	/* This special bean needs to be static to resolve "${}" string in @Value injection. */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer a = new PropertySourcesPlaceholderConfigurer();
		return a;
	}
}
