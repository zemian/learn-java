package com.zemian.hellojava.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * The home and landing page of the application.
 */
@Controller
public class AdminIndexController {
    @GetMapping({"/admin", "/admin/index"})
    public ModelAndView index() {
        ModelAndView result = new ModelAndView("/admin/index");
        return result;
    }
}
