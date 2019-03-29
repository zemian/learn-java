package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * This is the startup of the WebApp. Use {@link AppSpringConfig} to cusotmize Spring app, and keep
 * this class simple for bootstraping.
 *
 * NOTE: The AppStartup iteself is just a normal Java class, while WebInit is a Spring bean that
 * triggered with init lifecycle.
 */
public class AppStartup extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(AppSpringConfig.class);
	}

	// A spring component to initialize webapp context
	@Component
	public static class WebInit {
		@Autowired
		private ServletContext ctx;

		@Autowired
		private Environment env;

		@PostConstruct
		public void init() {
			// Setup global context app variables for easy access in view layer.
			Map<String, Object> app = new HashMap<>();
			app.put("contextPath", ctx.getContextPath());
			app.put("env", env.getProperty("app.env"));
			ctx.setAttribute("app", app);
		}
	}
}