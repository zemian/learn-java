package com.zemian.hellojava;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * A direct simple controller to forward URL to a view wihtout providing any data model.
 * Note that we can map multiple simple URL path here for the entire application.
 */
@WebServlet({"login", "login-error", "/about", "/error", "/hello"})
public class ViewController extends HttpServlet {
    private static final Logger LOG = getLogger(ViewController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppManager app = AppManager.getInstance();
        String name = req.getRequestURI();
        // Strip leading slash
        name = name.substring(1);
        String viewName = app.getViewResolver().resolveViewName(name);
        LOG.trace("Using viewName={}", viewName);
        req.getRequestDispatcher(viewName).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
