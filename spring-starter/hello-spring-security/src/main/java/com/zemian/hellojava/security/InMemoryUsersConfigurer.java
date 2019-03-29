package com.zemian.hellojava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Simple Spring Security starter. The base class should setup 'springSecurityFilterChain' automatically.
 *
 * By defautl, this setup will secured all URLs and use a plain built-in login form!
 */
public class InMemoryUsersConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                passwordEncoder(passwordEncoder);
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("test").password("$2a$05$R9PsUlsdXRb3qUuIwdLR8u8X4ZVNn6I/o6SBTCYy98MGlKFXokCHW").roles("USER").build());
        return manager;
    }
}
