package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppInit {
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
