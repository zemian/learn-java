package com.zemian.hellojava.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public class HttpSecurityHelper {
    /*
    Configure a typical webapplication that secure all UIs but allowed css and related resources.
    We support a custom login screen and error page.
     */
    public static void configureHttpSecurity(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/error").permitAll().
                antMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll().
                anyRequest().authenticated().and().
                formLogin().loginPage("/login").failureUrl("/login-error").permitAll().and().
                logout().logoutUrl("/logout").permitAll().and().
                csrf().disable();
    }
}
