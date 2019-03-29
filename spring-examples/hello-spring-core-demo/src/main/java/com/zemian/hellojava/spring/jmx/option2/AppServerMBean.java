package com.zemian.hellojava.spring.jmx.option2;

import java.util.Date;

public interface AppServerMBean {
    Date getStartupDate();
    String[] getBeanDefinitionNames();
}
