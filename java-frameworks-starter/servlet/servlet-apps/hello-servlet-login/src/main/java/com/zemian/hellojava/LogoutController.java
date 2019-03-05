package com.zemian.hellojava;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    private static final Logger LOG = getLogger(LogoutController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getUserPrincipal().getName();
        req.logout();
        LOG.info("User {} has logged out.", user);
        resp.sendRedirect("/");
    }
}
