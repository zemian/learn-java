package com.zemian.hellojava.spring.componentscan.halftscan;

import org.springframework.beans.factory.annotation.Autowired;

public class Hello {
    @Autowired
    private HelloB helloB;

    public HelloB getHelloB() {
        return helloB;
    }
}
