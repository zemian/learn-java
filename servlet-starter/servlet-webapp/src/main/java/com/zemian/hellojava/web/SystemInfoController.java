package com.zemian.hellojava.web;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

@WebServlet("/system-info")
public class SystemInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AppManager app = AppManager.getInstance();

        String fullParam = req.getParameter("full");
        if (fullParam == null)
            fullParam = "false";
        boolean full = Boolean.valueOf(fullParam);

        Map<String, String> sysInfo = new TreeMap<>();
        Properties sysProps = System.getProperties();
        if (full) {
            // Get full system props
            for (String name : sysProps.stringPropertyNames()) {
                sysInfo.put(name, sysProps.getProperty(name));
            }
        } else {
            // Get only small set of system props
            for (String name : sysProps.stringPropertyNames()) {
                if (name.startsWith("os") || name.startsWith("java.vm.specification")) {
                    sysInfo.put(name, sysProps.getProperty(name));
                }
            }
        }
        req.setAttribute("sysInfo", sysInfo);

        String viewName = app.getViewResolver().resolveViewName("system-info");
        req.getRequestDispatcher(viewName).forward(req, resp);
    }
}
