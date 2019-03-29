package com.zemian.hellojava;

import com.zemian.hellojava.spring.app.AppMainConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(AppMainConfig.class)
public class SpringTestConfig {
}
