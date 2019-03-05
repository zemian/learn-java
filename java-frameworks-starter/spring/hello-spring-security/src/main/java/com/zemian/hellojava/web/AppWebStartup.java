package com.zemian.hellojava.web;

import com.zemian.hellojava.security.AppSecurityConfig;
import com.zemian.hellojava.service.CommonServiceConfig;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * This is the startup of the WebApp. Use {@link AppWebConfig} to cusotmize Spring web app, and keep
 * this class simple for bootstraping.
 */
public class AppWebStartup extends AbstractAnnotationConfigDispatcherServletInitializer {
	@Override
	protected String[] getServletMappings() {
		return new String[]{ "/" };
	}

	@Nullable
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{ AppWebConfig.class };
	}

	@Nullable
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{ CommonServiceConfig.class, AppSecurityConfig.class };
	}

}