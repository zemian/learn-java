package com.zemian.hellojava.service;


import com.zemian.hellojava.service.CommonServiceConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/*
This class override the one defined in hello-spring-common with same package name.
We want this class so we can experiment other service config demos.
 */
@Configuration
@Import(CommonServiceConfig.class)
public class ServiceConfig {
}
