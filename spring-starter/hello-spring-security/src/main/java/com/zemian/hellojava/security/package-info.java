package com.zemian.hellojava.security;

/*

This package contains many example of Spring java config related to Spring Security. User need
to import only ONE of the configuration to use it. See `AppSecurityConfig` class.

== How SpringSecurity work

https://docs.spring.io/spring-security/site/docs/4.2.3.RELEASE/reference/html/technical-overview.html

9.2.4 Summary
Just to recap, the major building blocks of Spring Security that we’ve seen so far are:

    SecurityContextHolder, to provide access to the SecurityContext.
    SecurityContext, to hold the Authentication and possibly request-specific security information.
    Authentication, to represent the principal in a Spring Security-specific manner.
    GrantedAuthority, to reflect the application-wide permissions granted to a principal.
    UserDetails, to provide the necessary information to build an Authentication object from your application’s DAOs or other source of security data.
    UserDetailsService, to create a UserDetails when passed in a String-based username (or certificate ID or the like).

== Authentication

UserDetailsService / JdbcUserDetailsManager - loadUserDetails(String username)

AbstractSecurityInterceptor - ExceptionTranslationFilter or AuthenticationEntryPoint

    AuthenticationManager validate username & password and return Authentication

UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrinciple();

== Authorization

AbstractSecurityInterceptor

    AccessDecisionManager - "Secured Object", ConfigAttribute, Authentication,

== Configuration

You need the following setup to enable SpringSecurity for an webapp:
1. A `AppSecurityConfig` class that extends WebSecurityConfigurerAdapter and @EnableWebSecurity
2. Register `springSecurityFilterChain` in servlet container with a WebApplicationInitializer
3. The `AppSecurityConfig` must be loaded as part of root of ApplicationContext.
4. Setup views for "/login", "login-error", and "logout".

== Differences between HttpSecurity, WebSecurity and AuthenticationManagerBuilder

https://stackoverflow.com/questions/22998731/httpsecurity-websecurity-and-authenticationmanagerbuilder

* configure(AuthenticationManagerBuilder) is used to establish an authentication mechanism by allowing AuthenticationProviders to be added easily:
* configure(HttpSecurity) allows configuration of web based security at a resource level, based on a selection match - e.g.
* configure(WebSecurity) is used for configuration settings that impact global security (ignore resources, set debug mode, reject requests by implementing a custom firewall definition).

 */