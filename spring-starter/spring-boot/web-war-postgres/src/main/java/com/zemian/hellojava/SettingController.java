package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SettingController {
    @Autowired
    private SettingDAO settingDAO;

    @GetMapping("/settings")
    public ModelAndView getSettings() {
        List<Setting> settings = settingDAO.findAll();
        ModelAndView result = new ModelAndView("/settings");
        result.addObject("settings", settings);
        return result;
    }
}
