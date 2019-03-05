package com.zemian.hellojava.web;

import org.slf4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Main entry point into the web application. This is where we init/destroy the AppManager.
 */
@WebListener
public class AppStartup implements ServletContextListener {
	private static final Logger LOG = getLogger(AppStartup.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext ctx = servletContextEvent.getServletContext();
		AppManager app = AppManager.getInstance();
		app.setContextPath(ctx.getContextPath());
		app.init();
		ctx.setAttribute("app", app);
		LOG.info("Application started");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		AppManager app = AppManager.getInstance();
		app.destroy();
		LOG.info("Application stopped");
	}
}