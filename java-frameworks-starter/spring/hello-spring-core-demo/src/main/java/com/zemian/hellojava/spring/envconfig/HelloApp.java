package com.zemian.hellojava.spring.envconfig;

import com.zemian.hellojava.DataGenerator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/*
This program demonstrate how to load app properties with different spring profile.

It's consider good practice to place resources under the application name (eg: 'classpath:/hellojava')
to avoid name conflict with other applications.

You may run this main class with different spring profile. Eg:

-Dspring.profiles.active=prod

Or
-Dspring.profiles.active=qa

Or just omit to use the built-in (DEV)

 */
public class HelloApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext spring = new AnnotationConfigApplicationContext(AppConfig.class);

        ConfigurableEnvironment env = spring.getEnvironment();
        System.out.println("Env=" + env);

        DataGenerator bean = spring.getBean(DataGenerator.class);
        System.out.println("DataGenerator=" + bean);
    }
}
