package com.zemian.hellojava.spring.componentscan;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        basePackages = "com.zemian.hellojava.spring.componentscan",
        excludeFilters =
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.zemian\\.hellojava\\.spring\\.componentscan\\.app2.*")
)
public class App1AutoScanConfig {
}
