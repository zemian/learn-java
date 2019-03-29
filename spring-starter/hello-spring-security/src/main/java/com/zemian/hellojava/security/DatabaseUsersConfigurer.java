package com.zemian.hellojava.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

/**
 * User may extends this for their web security configuration.
 *
 * This requires you to setup database source with users and authorities tables. See `db/create-schama.sql`.
 *
 * Use `CreateDbUserTest` to generate test users.
 */
public class DatabaseUsersConfigurer extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurityHelper.configureHttpSecurity(http);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().
                dataSource(dataSource).
                passwordEncoder(passwordEncoder);
    }
}
