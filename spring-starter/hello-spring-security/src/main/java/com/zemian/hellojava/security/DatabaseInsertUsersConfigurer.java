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
 * This configuration also will auto insert the test users configured here for the first time! Afterword
 * it will fail since you can not have duplicated users. So this will only work one time test.
 */
public class DatabaseInsertUsersConfigurer extends WebSecurityConfigurerAdapter {
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
                withUser("admin").password("$2a$05$Exj9iNyCq.aDUVxi7HPp6egkFJbQF0BtWC1zgCusVB5AI/aMsakQG").roles("ADMIN").and().
                withUser("test").password("$2a$05$R9PsUlsdXRb3qUuIwdLR8u8X4ZVNn6I/o6SBTCYy98MGlKFXokCHW").roles("USER").and().
                withUser("test2").password("$2a$05$MtRC/pjZV/77BXW9WoCpCuNHuLPb5yDqaqQnX.0W3YbKN3kbToGZy").roles("USER");
    }
}
