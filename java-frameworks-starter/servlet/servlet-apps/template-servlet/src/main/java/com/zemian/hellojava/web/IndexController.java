package com.zemian.hellojava.web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * App landing page.
 */
@WebServlet({"/index", "/index.jsp", "/index.html"})
public class IndexController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppManager app = AppManager.getInstance();
        String viewName = app.getViewResolver().resolveViewName("index");
        req.getRequestDispatcher(viewName).forward(req, resp);
    }
}
