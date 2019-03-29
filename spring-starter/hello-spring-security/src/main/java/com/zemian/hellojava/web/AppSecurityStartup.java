package com.zemian.hellojava.web;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * This webapp initializer will register `springSecurityFilterChain` filter from `AppSecurityCofnig`.
 *
 * Note you will need to add `AppSecurityCofnig` in the AppWebStartup initializer as well.
 */
public class AppSecurityStartup extends AbstractSecurityWebApplicationInitializer {
}