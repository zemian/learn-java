package com.zemian.hellojava.spring.tx;

import com.zemian.hellojava.data.domain.Setting;
import com.zemian.hellojava.data.domain.User;
import com.zemian.hellojava.support.JavaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class TxServiceTestController {
    @Autowired
    TxService txService;

    @Autowired
    NoTxService noTxService;

    @Autowired
    TemplateTxService templateTxService;

    @GetMapping("/api/test/tx-service")
    @ResponseBody
    public Map<String, Object> txService(TestParam testParam) {
        String settingName = (testParam.getSettingName() == null ?
                "TxServiceTestController_test" : testParam.getSettingName());
        String username = (testParam.getUsername() == null ?
                "TxServiceTestController_test" : testParam.getUsername());
        User user = new User(username, "foo");
        Setting setting = new Setting("TEST", settingName , "foo");
        txService.create(testParam, setting, user);

        return JavaUtils.map(
                "type", "noTxService",
                "result", "Settings created " + setting);
    }

    @GetMapping("/api/test/template-tx-service")
    @ResponseBody
    public Map<String, Object> templateTxService(TestParam testParam) {
        String settingName = (testParam.getSettingName() == null ?
                "TxServiceTestController_test" : testParam.getSettingName());
        String username = (testParam.getUsername() == null ?
                "TxServiceTestController_test" : testParam.getUsername());
        User user = new User(username, "foo");
        Setting setting = new Setting("TEST", settingName , "foo");
        templateTxService.create(testParam, setting, user);

        return JavaUtils.map(
                "type", "templateTxService",
                "result", "Settings created " + setting);
    }

    @GetMapping("/api/test/no-tx-service")
    @ResponseBody
    public Map<String, Object> noTxService(TestParam testParam) {
        String settingName = (testParam.getSettingName() == null ?
                "TxServiceTestController_test" : testParam.getSettingName());
        String username = (testParam.getUsername() == null ?
                "TxServiceTestController_test" : testParam.getUsername());
        User user = new User(username, "foo");
        Setting setting = new Setting("TEST", settingName , "foo");
        noTxService.create(testParam, setting, user);

        return JavaUtils.map(
                "type", "noTxService",
                "result", "Settings created " + setting);
    }
}
