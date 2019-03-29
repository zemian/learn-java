package com.zemian.hellojava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloService {
    @Value("${app.env}")
    private String appEnv;

    @Value("${app.hello.message}")
    private String message;

    @GetMapping("/appenv")
    public String getAppEnv() {
        return appEnv;
    }

    @GetMapping("/hello")
    public String getGreetingMessage() {
        return message;
    }
}
