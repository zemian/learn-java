package com.zemian.hellojava.spring.componentscan.app1;

import com.zemian.hellojava.spring.componentscan.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyService1 implements MyService {
    private static final Logger LOG = LoggerFactory.getLogger(MyService1.class);

    @Override
    public String getName() {
        return MyService1.class.getSimpleName();
    }

    @Override
    public void run() {
        LOG.info(getName() + " in action.");
    }
}
