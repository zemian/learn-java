package com.zemian.hellojava;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/settings")
public class SettingController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppManager app = AppManager.getInstance();
        List<Setting> settings = app.getSettingDAO().findAll();
        String viewName = app.getViewResolver().resolveViewName("/settings");
        req.setAttribute("settings", settings);
        req.getRequestDispatcher(viewName).forward(req, resp);
    }
}
