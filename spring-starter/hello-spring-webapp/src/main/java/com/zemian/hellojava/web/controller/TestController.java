package com.zemian.hellojava.web.controller;

import com.zemian.hellojava.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@Controller
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test")
    public ModelAndView index() {
        return new ModelAndView("/test/index");
    }

    @GetMapping("/test/{viewName}")
    public ModelAndView testView(@PathVariable String viewName, HttpServletRequest req) {
        LOG.info("Testing viewName={}", viewName);

        if ("error".equals(req.getParameter("test"))) {
            LOG.info("Testing exception on purpose.");
            throw new AppException("Test error.");
        }

        ModelAndView result = new ModelAndView("/test/" + viewName);

        // Add some sample data
        result.addObject("sysProps", System.getProperties());
        result.addObject("sysEnv", System.getenv());
        result.addObject("currentDirFiles", new File(".").listFiles());
        result.addObject("currentTimeMillis", System.currentTimeMillis());
        result.addObject("currentDate", new Date());
        result.addObject("java8LocalDateTime", LocalDateTime.now());

        // Get a list of Session key names if any
        HttpSession httpSession = req.getSession(false);
        if (httpSession != null) {
            List<String> nameList = new ArrayList<>();
            Enumeration<String> names = httpSession.getAttributeNames();
            while (names.hasMoreElements()) {
                nameList.add(names.nextElement());
            }
            result.addObject("sessionVarNames", nameList);
        }
        // Get a list of Session key names if any
        ServletContext servletContext = req.getServletContext();
        List<String> nameList = new ArrayList<>();
        Enumeration<String> names = servletContext.getAttributeNames();
        while (names.hasMoreElements()) {
            nameList.add(names.nextElement());
        }
        result.addObject("applicationVarNames", nameList);
        return result;
    }
}
