package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Main Spring Java Config entry.
 */
@SpringBootApplication
public class AppSpringConfig {
    /**
     * Customize Spring MVC specific. Many of these can be set in SpringBoot's application.properties,
     * but some we need to customize here.
     */
    @Configuration
    public static class AppWebMvcConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/about").setViewName("about");
            registry.addViewController("/login").setViewName("login");
            registry.addViewController("/login-error").setViewName("login-error");
        }
    }

    /**
     * Customize Spring Security.
     */
    @Configuration
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().
                    antMatchers("/error").permitAll().
                    antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll().
                    anyRequest().authenticated().
                        and().
                    formLogin().
                        loginPage("/login").
                        failureUrl("/login-error").
                        permitAll().
                        and().
                    logout().
                        logoutUrl("/logout").
                        permitAll().
                        and().
                    csrf().
                        disable();
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            // Example User Query: select username,password,enabled from users where username = ?
            // Example Authorities Query: select username,authority from authorities where username = ?
            auth.jdbcAuthentication().
                    dataSource(jdbcTemplate.getDataSource()).
                    usersByUsernameQuery("SELECT user_name, user_pass, TRUE FROM users WHERE user_name = ?").
                    authoritiesByUsernameQuery("SELECT user_name, role_name FROM user_roles WHERE user_name = ?");
        }
    }
}
