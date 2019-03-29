package com.zemian.bds.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Properties;

@SpringBootApplication
@Controller
public class Example {
    public static void main(String[] args) {
        SpringApplication.run(Example.class, args);
    }

    @GetMapping("/")
    @ResponseBody
    public Properties hello() {
        return System.getProperties();
    }
}
