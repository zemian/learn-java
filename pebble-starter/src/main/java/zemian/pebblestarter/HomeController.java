package zemian.pebblestarter;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.mitchellbosecke.pebble.*;
import com.mitchellbosecke.pebble.loader.ServletLoader;
import com.mitchellbosecke.pebble.template.PebbleTemplate;

import java.io.Writer;
import java.util.*;

/**
 * Application home/landing page.
 */
@WebServlet("/")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletLoader loader = new ServletLoader(req.getServletContext());
        loader.setPrefix("/WEB-INF/pebble");
        loader.setSuffix(".html");
        PebbleEngine engine = new PebbleEngine.Builder().loader(loader).build();
        PebbleTemplate compiledTemplate = engine.getTemplate("home");

        Map<String, Object> context = new HashMap<>();
        context.put("name", "Zemian");

        Writer writer = resp.getWriter();
        compiledTemplate.evaluate(writer, context);
    }
}
