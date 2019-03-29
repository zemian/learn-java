package com.zemian.hellojava;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The home and landing page of the application.
 */
@Controller
public class IndexController {
    @GetMapping(value = {"/", "/index", "/index.jsp", "/index.html"})
    public ModelAndView index() {
        ModelAndView result = new ModelAndView("index");
        return result;
    }
}
