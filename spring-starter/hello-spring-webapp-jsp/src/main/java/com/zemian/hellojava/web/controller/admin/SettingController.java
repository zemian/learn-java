package com.zemian.hellojava.web.controller.admin;

import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.dao.PagingList;
import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("/admin/settings")
    public ModelAndView list(Paging paging) {
        PagingList<Setting> settings = settingService.find(paging);
        ModelAndView result = new ModelAndView("/admin/settings");
        result.addObject("settings", settings);
        return result;
    }
}
