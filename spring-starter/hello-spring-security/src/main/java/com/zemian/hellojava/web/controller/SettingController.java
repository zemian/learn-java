package com.zemian.hellojava.web.controller;

import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("/settings")
    public ModelAndView getSettings() {
        List<Setting> settings = settingService.find(new Paging()).getList();
        ModelAndView result = new ModelAndView("/settings");
        result.addObject("settings", settings);
        return result;
    }
}
