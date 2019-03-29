package com.zemian.hellojava.spring.componentscan.app2;

import com.zemian.hellojava.spring.componentscan.MyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MyService2 implements MyService {
    private static final Logger LOG = LoggerFactory.getLogger(MyService2.class);

    @Override
    public String getName() {
        return MyService2.class.getSimpleName();
    }

    @Override
    public void run() {
        LOG.info(getName() + " in action.");
    }
}
