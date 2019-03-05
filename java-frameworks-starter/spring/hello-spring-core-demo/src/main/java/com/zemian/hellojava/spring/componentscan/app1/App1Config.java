package com.zemian.hellojava.spring.componentscan.app1;

import com.zemian.hellojava.Hello;
import com.zemian.hellojava.spring.componentscan.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A java config for app2
 */
@Configuration
public class App1Config {
	@Bean
	public Hello helloService() {
		return new Hello();
	}

	@Bean
	public MyService myService() {
		return new MyService1();
	}
}
