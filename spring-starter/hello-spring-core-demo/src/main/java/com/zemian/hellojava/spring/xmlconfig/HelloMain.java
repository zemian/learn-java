package com.zemian.hellojava.spring.xmlconfig;

import com.zemian.hellojava.Hello;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloMain {
    private static Logger LOG = LoggerFactory.getLogger(HelloMain.class);

    public static void main(String[] args) {
        String config = "com/zemian/hellojava/spring/xmlconfig/hello-config.xml";
        ConfigurableApplicationContext spring = new ClassPathXmlApplicationContext(config);
        Hello hello = spring.getBean(Hello.class);
        LOG.info("Greeting bean={}", hello.getName());
        spring.close();
    }
}
