package com.zemian.hellojava.spring.envconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:/hellojava/app-qa.properties")
@Profile("qa")
public class AppConfigQa {
}
