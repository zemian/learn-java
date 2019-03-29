package zemian.servletstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(HelloServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext ctx = req.getServletContext();
        // You need to enable logger conf to see this line!
        ctx.log("Processing request " + req.getRequestURI());

        String testParam = req.getParameter("test");
        LOG.info("Processing method={}, uri={}, testParam={}", req.getMethod(), req.getRequestURI(), testParam);

        if ("emptyResponse".equals(testParam)) {
            LOG.info("Generating empty response");
            return;
        } else {
            LOG.info("Serving hello message");
            resp.getWriter().write("Hello");
            resp.getWriter().flush();
        }
    }
}
