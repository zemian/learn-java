package ${basePackage}.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * A spring component to initialize webapp context
 */
@Component
public class AppWebInit {
    @Autowired
    private ServletContext ctx;

    @PostConstruct
    public void init() {
        Map<String, Object> config = new HashMap<>();
        config.put("app.env", "DEV");
        config.put("app.web.name", "${projectName}");
        config.put("app.web.htmlTitle", "${projectName}");

        // Setup global context app variables for easy access in view layer.
        Map<String, Object> app = new HashMap<>();
        app.put("contextPath", ctx.getContextPath());
        app.put("config", config);
        ctx.setAttribute("app", app);
    }
}
