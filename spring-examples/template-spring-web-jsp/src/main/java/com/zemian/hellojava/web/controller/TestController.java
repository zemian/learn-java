package com.zemian.hellojava.web.controller;

import com.zemian.hellojava.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @GetMapping("/test/{viewName}")
    public ModelAndView hello(@PathVariable String viewName, HttpServletRequest req) {
        LOG.info("Testing viewName={}", viewName);

        if ("error".equals(req.getParameter("test"))) {
            throw new AppException("Test error.");
        }

        ModelAndView result = new ModelAndView("/test/" + viewName);
        return result;
    }
}
