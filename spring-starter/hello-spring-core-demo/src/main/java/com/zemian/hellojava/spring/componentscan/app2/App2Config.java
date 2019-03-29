package com.zemian.hellojava.spring.componentscan.app2;

import com.zemian.hellojava.spring.componentscan.MyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * A java config for app2
 */
@Configuration
public class App2Config {
	@Bean
	public MyService myService() {
		return new MyService2();
	}
}
