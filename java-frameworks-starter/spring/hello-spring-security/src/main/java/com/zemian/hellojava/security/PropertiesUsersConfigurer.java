package com.zemian.hellojava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Properties;

/**
 * User may extends this for their web security configuration.
 */
public class PropertiesUsersConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurityHelper.configureHttpSecurity(http);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                passwordEncoder(passwordEncoder);
    }

    @Bean
    public UserDetailsService userPropsUserDetailsService() throws Exception {
        Resource resource = new ClassPathResource("/hellojava/app-users.properties");
        Properties userProps = PropertiesLoaderUtils.loadProperties(resource);
        InMemoryUserDetailsManager bean = new InMemoryUserDetailsManager(userProps);
        return bean;
    }
}
