package com.zemian.hellojava.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Setup Spring Security for this Application.
 */
@Configuration
@EnableWebSecurity
@ComponentScan
public class AppSecurityConfig extends DatabaseUsersConfigurer {
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bean = new BCryptPasswordEncoder(5);
        return bean;
    }
}
