package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Main Spring Java Config entry.
 */
@SpringBootApplication
public class AppSpringConfig {

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

    /**
     * Customize Spring MVC specific. Many of these can be set in SpringBoot's application.properties,
     * but some we need to customize here.
     */
    @Configuration
    public static class AppWebMvcConfig extends WebMvcConfigurerAdapter {
        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/about").setViewName("about");
        }
    }
}
