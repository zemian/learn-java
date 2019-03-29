package com.zemian.hellojava.spring.hello;

import com.zemian.hellojava.spring.app.AppMainConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Just a demo config to provide the hello service bean. We will use auto scan to discover it.
 */
@Configuration
@Import(AppMainConfig.class)
@ComponentScan
public class HelloConfig {
}
