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
 *
 * This example config show how you can customize the queries for user and authrity table. But we are still
 * using the same tables created from our demo schema.
 */
public class DatabaseUsersCustomTableConfigurer extends WebSecurityConfigurerAdapter {
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
                passwordEncoder(passwordEncoder).
                usersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?").
                authoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
    }
}
