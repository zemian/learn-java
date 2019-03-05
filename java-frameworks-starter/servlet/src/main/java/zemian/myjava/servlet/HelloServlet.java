package zemian.myjava.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zemian on 10/22/16.
 */
@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    public static final Logger LOG = LoggerFactory.getLogger(HelloServlet.class);
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.info("Processing request: {}", req.getRequestURI());
        req.setAttribute("name", "World!");
        req.getRequestDispatcher("/WEB-INF/jsp/hello.jsp").forward(req,resp);
    }
}
