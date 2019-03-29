package com.zemian.hellojava.web.controller.api;

import com.zemian.hellojava.data.dao.Paging;
import com.zemian.hellojava.data.dao.PagingList;
import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * API response.
 */
@RestController
public class ApiSettingController {
    @Autowired
    private SettingService settingService;

    @GetMapping("/api/setting/list")
    public @ResponseBody ApiResult<PagingList<Setting>> list(Paging paging) {
        PagingList<Setting> plist = settingService.find(paging);
        return ApiResult.result(plist);
    }

    @GetMapping("/api/setting/{settingId}")
    public @ResponseBody ApiResult<Setting> get(@PathVariable Integer settingId) {
        return ApiResult.result(settingService.get(settingId));
    }

    @PostMapping("/api/setting/create")
    public @ResponseBody ApiResult<Setting> create(@RequestBody Setting setting) {
        settingService.create(setting);
        return ApiResult.result(setting);
    }

    @PostMapping("/api/setting/update")
    public @ResponseBody ApiResult<Setting> update(@RequestBody Setting setting) {
        settingService.update(setting);
        return ApiResult.result(setting);
    }

    @PostMapping("/api/setting/delete")
    public @ResponseBody ApiResult<Setting> delete(@RequestBody Setting setting) {
        Setting ret = settingService.get(setting.getSettingId());
        settingService.delete(setting.getSettingId());
        return ApiResult.result(ret);
    }
}
