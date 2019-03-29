package com.zemian.hellojava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HelloService {
    private static Logger LOG = LoggerFactory.getLogger(HelloService.class);

    @Autowired
    private SettingDAO settingDAO;

    public String getGreetingMessage() {
        return "Hello World";
    }

    public void run() throws Exception {
        List<Setting> settings = settingDAO.findAll();
        for (Setting setting : settings) {
            LOG.info("Record: {}", setting);
        }
    }
}
