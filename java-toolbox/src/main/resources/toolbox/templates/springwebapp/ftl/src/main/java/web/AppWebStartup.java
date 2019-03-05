package ${basePackage}.web;

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

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[]{ AppWebConfig.class };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[]{ };
	}
}
